/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.data.SubmissionStatus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.exceptions.CancelledException;
import io.github.thepieterdc.dodona.plugin.exceptions.warnings.SubmissionTimeoutException;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.exercise.identification.IdentificationService;
import io.github.thepieterdc.dodona.plugin.feedback.FeedbackService;
import io.github.thepieterdc.dodona.plugin.notifications.ErrorReporter;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.submission.SubmissionCreatedListener;
import io.github.thepieterdc.dodona.plugin.submission.SubmissionEvaluatedListener;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.Submission;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Submits code to Dodona.
 */
public class SubmitSolutionTask extends AbstractDodonaBackgroundTask {
	private static final double DELAY_BACKOFF_FACTOR = 1.15;
	private static final long DELAY_INITIAL_MS = 3_000L;
	private static final long DELAY_TIMEOUT_MS = 120_000L;
	private static final long DELAY_MAX_WAIT_MS = 20_000L;
	private static final long DELAY_MIN_WAIT_MS = 2_000L;
	
	private static final double PROGRESS_SUBMITTED = 0.5;
	
	private final MessageBus bus;
	
	private final String code;
	private final Identification identification;
	
	private final DodonaExecutor executor;
	private final FeedbackService feedback;
	private final DodonaProjectSettings projectSettings;
	
	/**
	 * SubmitSolutionTask constructor.
	 *
	 * @param project  the current project
	 * @param exercise the exercise to submit to
	 * @param solution the solution to submit
	 */
	private SubmitSolutionTask(final Project project,
	                           final Identification exercise,
	                           final String solution) {
		super(project, DodonaBundle.message("tasks.submit_solution.title"));
		this.bus = project.getMessageBus();
		this.code = solution;
		this.executor = DodonaAuthenticator.getInstance().getExecutor();
		this.feedback = FeedbackService.getInstance(project);
		this.identification = exercise;
		this.projectSettings = DodonaProjectSettings.getInstance(project);
	}
	
	/**
	 * Awaits until the submission is evaluated.
	 *
	 * @param progress     the progress indicator
	 * @param exercise     the exercise
	 * @param submissionId the id of the submission
	 * @return the submission
	 */
	@Nonnull
	private Submission awaitEvaluation(final ProgressIndicator progress,
	                                   final Exercise exercise,
	                                   final long submissionId) throws InterruptedException {
		Submission submission = null;
		
		boolean broadcasted = false;
		
		// Perform exponential backoff until the solution is accepted or the
		// timeout is reached.
		long currentDelay = DELAY_INITIAL_MS;
		long totalWaited = 0L;
		while (submission == null
			|| submission.getStatus() == SubmissionStatus.RUNNING
			|| submission.getStatus() == SubmissionStatus.QUEUED) {
			
			// Check for timeouts.
			if (currentDelay < DELAY_MIN_WAIT_MS) {
				throw new SubmissionTimeoutException(exercise, submission);
			}
			
			// Await the delay.
			Thread.sleep(currentDelay);
			
			totalWaited += currentDelay;
			
			// Refresh the status.
			submission = this.executor.execute(
				dodona -> dodona.submissions().get(submissionId),
				progress
			);
			
			// Broadcast the created submission.
			if (!broadcasted) {
				this.bus.syncPublisher(SubmissionCreatedListener.SUBMISSION_CREATED)
					.onSubmissionCreated(submission);
				broadcasted = true;
			}
			
			// Determine the next delay amount.
			currentDelay = Math.min(
				(long) (((double) currentDelay) * DELAY_BACKOFF_FACTOR),
				DELAY_MAX_WAIT_MS
			);
			
			// Ensure the delay is still within bounds.
			currentDelay = Math.min(
				DELAY_TIMEOUT_MS - totalWaited,
				currentDelay
			);
		}
		
		return submission;
	}
	
	/**
	 * Creates a code submission task from the given code.
	 *
	 * @param project        the current project
	 * @param identification the exercise to submit the solution to
	 * @param code           the code to submit
	 * @return the task
	 */
	@Nonnull
	public static DodonaBackgroundTask create(final Project project,
	                                          final Identification identification,
	                                          final String code) {
		return new SubmitSolutionTask(project, identification, code);
	}
	
	/**
	 * Identifies the exercise and creates a code submission task from the given
	 * code.
	 *
	 * @param project the current project
	 * @param code    the code to submit
	 * @return the task
	 */
	@Nonnull
	public static DodonaBackgroundTask create(final Project project,
	                                          final String code) {
		// Attempt to identify the exercise, otherwise return a new task to
		// perform this job.
		return IdentificationService.getInstance()
			.identify(code)
			.map(result -> create(project, result, code))
			.orElseThrow(RuntimeException::new);
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progress) {
		try {
			// Set the project course if this had not been set yet.
			this.identification.getCourseId().ifPresent(this.projectSettings::setCourseId);
			
			// Update the progress bar.
			progress.setIndeterminate(true);
			progress.setText(
				DodonaBundle.message("tasks.submit_solution.submitting")
			);
			
			// Submit the solution and get the id of the submission.
			final long id = this.executor.execute(this::submit, progress);
			
			// Get information about the exercise.
			final Exercise exercise = this.executor.execute(
				dodona -> dodona.exercises().get(this.identification.getExerciseId()),
				progress
			);
			
			// Update the progress bar.
			progress.setIndeterminate(false);
			progress.setFraction(PROGRESS_SUBMITTED);
			progress.setText(DodonaBundle.message("tasks.submit_solution.evaluating"));
			
			// Await the evaluation.
			final Submission evaluated = this.awaitEvaluation(progress, exercise, id);
			
			// Broadcast the evaluation result.
			this.bus.syncPublisher(SubmissionEvaluatedListener.SUBMISSION_EVALUATED)
				.onSubmissionEvaluated(evaluated);
			
			// Update the progess bar.
			progress.setFraction(1.0);
			progress.setText(DodonaBundle.message("tasks.submit_solution.evaluated"));
			
			// Provide feedback to the user.
			this.feedback.notify(exercise, evaluated.getInfo());
		} catch (final InterruptedException ex) {
			throw new CancelledException();
		} catch (final RuntimeException ex) {
			ErrorReporter.report(ex);
		}
	}
	
	/**
	 * Submits the solution.
	 *
	 * @param client the Dodona client
	 * @return the submission id
	 */
	private long submit(final DodonaClient client) {
		return client.submissions().create(
			this.identification.getCourseId().orElse(null),
			this.identification.getSeriesId().orElse(null),
			this.identification.getExerciseId(),
			this.code
		);
	}
}