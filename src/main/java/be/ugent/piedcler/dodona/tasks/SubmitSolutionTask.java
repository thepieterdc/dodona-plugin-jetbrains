/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.tasks;

import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.dto.Submission;
import be.ugent.piedcler.dodona.dto.submission.SubmissionStatus;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.exceptions.warnings.IncorrectSubmissionException;
import be.ugent.piedcler.dodona.exceptions.warnings.SubmissionTimeoutException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.services.SubmissionService;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Submits a solution to Dodona.
 */
public class SubmitSolutionTask extends Task.Backgroundable {
	private static final double DELAY_BACKOFF_FACTOR = 1.15;
	private static final long DELAY_MAX = 20_000L;
	private static final long DELAY_INITIAL = 3_000L;
	private static final long DELAY_TIMEOUT = 120_000L;
	
	private final Solution solution;
	private final SubmissionService submissions;
	
	/**
	 * SubmitSolutionTask constructor.
	 *
	 * @param project  the project to display notifications in
	 * @param solution the solution to submit
	 */
	public SubmitSolutionTask(final Project project, final Solution solution) {
		super(project, String.format("Evaluation for %s", solution.getExercise().getName()));
		this.solution = solution;
		this.submissions = SubmissionService.getInstance();
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {
		try {
			progressIndicator.setFraction(0.10);
			progressIndicator.setText("Submitting to Dodona...");
			
			Submission submission = this.submissions.submit(this.solution);
			NotificationReporter.info("Solution successfully submitted, awaiting evaluation.");
			
			progressIndicator.setFraction(0.50);
			progressIndicator.setText("Awaiting evaluation...");
			
			Thread.sleep(SubmitSolutionTask.DELAY_INITIAL);
			
			long delay = SubmitSolutionTask.DELAY_INITIAL;
			long total = 0L;
			while (submission.getStatus() == SubmissionStatus.PENDING) {
				if (total > DELAY_TIMEOUT) {
					break;
				}
				
				Thread.sleep(delay);
				
				submission = this.submissions.get(submission.getId());
				
				delay = Math.min(
					(long) ((double) delay * SubmitSolutionTask.DELAY_BACKOFF_FACTOR),
					SubmitSolutionTask.DELAY_MAX
				);
				
				total += delay;
			}
			
			if (submission.getStatus() == SubmissionStatus.PENDING) {
				throw new SubmissionTimeoutException(submission);
			}
			
			progressIndicator.setFraction(1.0);
			progressIndicator.setText("Evaluation completed");
			
			// Required to use EventQueue.invokeLater(), must be final.
			final Submission completed = submission;
			
			if (submission.getStatus() == SubmissionStatus.CORRECT) {
				EventQueue.invokeLater(() -> NotificationReporter.info(
					String.format("Solution to %s was correct!", completed.getExercise().getName())
				));
			} else {
				throw new IncorrectSubmissionException(completed);
			}
		} catch (final WarningMessageException warning) {
			EventQueue.invokeLater(() -> NotificationReporter.warning(warning.getMessage()));
		} catch (final ErrorMessageException error) {
			EventQueue.invokeLater(() -> NotificationReporter.error(error.getMessage()));
		} catch (final InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
}
