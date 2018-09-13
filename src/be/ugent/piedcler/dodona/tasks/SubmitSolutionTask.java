/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.tasks;

import be.ugent.piedcler.dodona.api.Exercises;
import be.ugent.piedcler.dodona.api.Submissions;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Submission;
import be.ugent.piedcler.dodona.dto.submission.SubmissionStatus;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.exceptions.warnings.IncorrectSubmissionException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Submits a solution to Dodona.
 */
public class SubmitSolutionTask extends Task.Backgroundable {
	private static final double DELAY_BACKOFF_FACTOR = 1.2;
	private static final long DELAY_MAX = 35_000L;
	private static final long DELAY_INITIAL = 2_000L;
	
	private final Exercise exercise;
	private final String solution;
	
	/**
	 * SubmitSolutionTask constructor.
	 *
	 * @param project  the project to display notifications in
	 * @param exercise the exercise to solve
	 * @param solution the solution
	 */
	public SubmitSolutionTask(final Project project, final Exercise exercise, final String solution) {
		super(project, String.format("Evaluation for %s", exercise.getName()));
		this.exercise = exercise;
		this.solution = solution;
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {
		try {
			progressIndicator.setFraction(0.10);
			progressIndicator.setText("Submitting to Dodona...");
			
			// TODO: check if the course and exercise exist and throw an error if not,
			// TODO  not entirely required but would probably be a nice improvement.
			
			Submission submission = Exercises.submit(this.exercise, this.solution);
			NotificationReporter.info("Solution successfully submitted, awaiting evaluation.");
			
			progressIndicator.setFraction(0.50);
			progressIndicator.setText("Awaiting evaluation...");
			
			Thread.sleep(SubmitSolutionTask.DELAY_INITIAL);
			
			long delay = SubmitSolutionTask.DELAY_INITIAL;
			while (submission.getStatus() == SubmissionStatus.PENDING) {
				Thread.sleep(delay);
				
				submission = Submissions.get(submission);
				
				delay = Math.min(
						(long) (delay * SubmitSolutionTask.DELAY_BACKOFF_FACTOR),
						SubmitSolutionTask.DELAY_MAX
				);
			}
			
			progressIndicator.setFraction(1.0);
			progressIndicator.setText("Evaluation completed");
			
			if (submission.getStatus() == SubmissionStatus.CORRECT) {
				NotificationReporter.info(String.format("Solution to %s was correct!", submission.getExercise().getName()));
			} else {
				throw new IncorrectSubmissionException(submission);
			}
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		} catch (final InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
}
