/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.apiclient.exceptions.accessdenied.ResourceAccessDeniedException;
import be.ugent.piedcler.dodona.apiclient.exceptions.accessdenied.RootAccessDeniedException;
import be.ugent.piedcler.dodona.apiclient.exceptions.apitoken.ApiTokenException;
import be.ugent.piedcler.dodona.apiclient.exceptions.notfound.ExerciseNotFoundException;
import be.ugent.piedcler.dodona.apiclient.exceptions.notfound.ResourceNotFoundException;
import be.ugent.piedcler.dodona.apiclient.responses.*;
import be.ugent.piedcler.dodona.plugin.ApiClient;
import be.ugent.piedcler.dodona.plugin.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.errors.SubmissionException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.IncorrectSubmissionException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.SubmissionTimeoutException;
import be.ugent.piedcler.dodona.plugin.reporting.NotificationReporter;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static java.lang.String.format;

/**
 * Submits a solution to Dodona.
 */
public class SubmitSolutionTask extends Task.Backgroundable {
	private static final double DELAY_BACKOFF_FACTOR = 1.15;
	private static final long DELAY_MAX = 20_000L;
	private static final long DELAY_INITIAL = 3_000L;
	private static final long DELAY_TIMEOUT = 120_000L;

	private final Solution solution;
	private final Presentation presentation;

	/**
	 * SubmitSolutionTask constructor.
	 *
	 * @param project  the project to display notifications in
	 * @param solution the solution to submit
	 */
	public SubmitSolutionTask(final Project project, final Presentation presentation, final Solution solution) {
		super(project, format("Evaluation for %s", solution.getExerciseId()));
		this.solution = solution;
		this.presentation = presentation;
	}

	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {

		try {
			this.presentation.setEnabled(false);
			progressIndicator.setFraction(0.10);
			progressIndicator.setText("Submitting to Dodona...");


			final ApiClient api = ApiClient.getInstance();


			SubmissionPost submissionPost = api.postSolution(solution);

			Submission submission = api.getSubmission(submissionPost.getUrl());
			NotificationReporter.info("Solution successfully submitted, awaiting evaluation.");

			progressIndicator.setFraction(0.50);
			progressIndicator.setText("Awaiting evaluation...");

			Thread.sleep(SubmitSolutionTask.DELAY_INITIAL);

			long delay = SubmitSolutionTask.DELAY_INITIAL;
			long total = 0L;
			while (submission.getStatus() == SubmissionStatus.RUNNING
				|| submission.getStatus() == SubmissionStatus.QUEUED) {
				if (total > DELAY_TIMEOUT) {
					break;
				}

				Thread.sleep(delay);

				submission = api.getSubmission(submissionPost.getUrl());

				delay = Math.min(
					(long) ((double) delay * SubmitSolutionTask.DELAY_BACKOFF_FACTOR),
					SubmitSolutionTask.DELAY_MAX
				);

				total += delay;
			}

			Exercise exercise = api.getExercise(submission.getExercise());

			if (submission.getStatus() == SubmissionStatus.RUNNING
				|| submission.getStatus() == SubmissionStatus.QUEUED) {
				throw new SubmissionTimeoutException(submission, exercise);
			}

			progressIndicator.setFraction(1.0);
			progressIndicator.setText("Evaluation completed");

			// Required to use EventQueue.invokeLater(), must be final.
			final Submission completed = submission;
			if (submission.getStatus() == SubmissionStatus.CORRECT) {
				EventQueue.invokeLater(() -> NotificationReporter.info(
					format("Solution to %s was correct!", exercise.getName())
				));
			} else {
				throw new IncorrectSubmissionException(completed, exercise);
			}
		} catch (final WarningMessageException warning) {
			EventQueue.invokeLater(() -> NotificationReporter.warning(warning.getMessage()));
		} catch (final ErrorMessageException
			| ApiTokenException
			| ResourceNotFoundException
			| ResourceAccessDeniedException error) {
			EventQueue.invokeLater(() -> NotificationReporter.error(error.getMessage()));
		} catch (final InterruptedException ex) {
			throw new RuntimeException(ex);
		} finally {
			this.presentation.setEnabled(true);
		}
	}
}
