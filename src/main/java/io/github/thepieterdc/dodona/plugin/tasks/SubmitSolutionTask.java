/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.tasks;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.data.SubmissionStatus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.code.Identification;
import io.github.thepieterdc.dodona.plugin.code.identification.IdentificationService;
import io.github.thepieterdc.dodona.plugin.exceptions.CancelledException;
import io.github.thepieterdc.dodona.plugin.exceptions.warnings.SubmissionTimeoutException;
import io.github.thepieterdc.dodona.plugin.feedback.FeedbackService;
import io.github.thepieterdc.dodona.plugin.notifications.ErrorReporter;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.Submission;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Submits code to Dodona.
 */
public class SubmitSolutionTask extends AbstractDodonaTask {
	private static final double DELAY_BACKOFF_FACTOR = 1.15;
	private static final long DELAY_INITIAL_MS = 3_000L;
	private static final long DELAY_TIMEOUT_MS = 120_000L;
	private static final long DELAY_MAX_WAIT_MS = 20_000L;
	private static final long DELAY_MIN_WAIT_MS = 2_000L;
	
	private static final double PROGRESS_SUBMITTED = 0.5;
	
	private final String code;
	private final Identification identification;
	
	private final DodonaExecutor executor;
	private final FeedbackService feedback;
	
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
		this.code = solution;
		this.executor = DodonaAuthenticator.getInstance().getExecutor();
		this.feedback = FeedbackService.getInstance(project);
		this.identification = exercise;
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
		
		long currentDelay = SubmitSolutionTask.DELAY_INITIAL_MS;
		long totalWaited = 0L;
		
		// Perform exponential backoff until the solution is accepted or the
		// timeout is reached.
		while (submission == null
			|| submission.getStatus() == SubmissionStatus.RUNNING
			|| submission.getStatus() == SubmissionStatus.QUEUED) {
			
			// Check for timeouts.
			if (currentDelay < SubmitSolutionTask.DELAY_MIN_WAIT_MS) {
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
			
			// Determine the next delay amount.
			currentDelay = Math.min(
				(long) (((double) currentDelay) * SubmitSolutionTask.DELAY_BACKOFF_FACTOR),
				SubmitSolutionTask.DELAY_MAX_WAIT_MS
			);
			
			// Ensure the delay is still within bounds.
			currentDelay = Math.min(
				SubmitSolutionTask.DELAY_TIMEOUT_MS - totalWaited,
				currentDelay
			);
		}
		
		return submission;
	}
	
	/**
	 * Creates a code submission task from the given code.
	 *
	 * @param project the current project
	 * @param code    the code to submit
	 * @return the task
	 */
	@Nonnull
	public static DodonaTask create(final Project project, final String code) {
		// Attempt to identify the exercise, otherwise return a new task to
		// perform this job.
		return ServiceManager.getService(IdentificationService.class)
			.identify(code)
			.map(result -> new SubmitSolutionTask(project, result, code))
			.orElseThrow(RuntimeException::new);
	}
	
	@Override
	public void execute() {
		ProgressManager.getInstance().run(this);
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progress) {
		try {
			// Update the progress bar.
			progress.setIndeterminate(true);
			
			// Submit the solution and get the id of the submission.
			final long id = this.executor.executeWithModal(
				this.myProject,
				DodonaBundle.message("tasks.submit_solution.submitting"),
				this::submit
			);
			
			// Get information about the exercise.
			final Exercise exercise = this.executor.execute(
				dodona -> dodona.exercises().get(this.identification.getExercise()),
				progress
			);
			
			// Update the progress bar.
			progress.setIndeterminate(false);
			progress.setFraction(SubmitSolutionTask.PROGRESS_SUBMITTED);
			progress.setText(DodonaBundle.message("tasks.submit_solution.evaluating"));
			
			// Await the evaluation.
			final Submission evaluated = this.awaitEvaluation(progress, exercise, id);
			
			// Update the progess bar.
			progress.setFraction(1.0);
			progress.setText(DodonaBundle.message("tasks.submit_solution.evaluated"));
			
			// Provide feedback to the user.
			this.feedback.notify(exercise, evaluated);
		} catch (final IOException error) {
			ErrorReporter.report(error);
		} catch (final InterruptedException ex) {
			throw new CancelledException();
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
			this.identification.getCourse().orElse(null),
			this.identification.getSeries().orElse(null),
			this.identification.getExercise(),
			this.code
		);
	}
}
