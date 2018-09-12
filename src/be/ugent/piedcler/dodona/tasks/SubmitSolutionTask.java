/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.tasks;

import be.ugent.piedcler.dodona.actions.SubmitAction;
import be.ugent.piedcler.dodona.api.Exercises;
import be.ugent.piedcler.dodona.api.Submissions;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import be.ugent.piedcler.dodona.dto.submission.Submission;
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
	private static final long PULL_DELAY = 10_000L;
	private static final long PULL_DELAY_INITIAL = 5_000L;
	
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
		super(project, String.format("Evaluation for %s", exercise));
		this.exercise = exercise;
		this.solution = solution;
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {
		try {
			progressIndicator.setFraction(0.10);
			progressIndicator.setText("Submitting to Dodona...");
			
			Submission submission = Exercises.submit(this.exercise, this.solution);
			NotificationReporter.info(String.format(
					"Submission successfully submitted. <a href=\"%s\">View details</a>",
					submission.getUrl()
			));
			
			progressIndicator.setFraction(0.50);
			progressIndicator.setText("Awaiting evaluation...");
			
			Thread.sleep(SubmitSolutionTask.PULL_DELAY_INITIAL);
			
			while (submission.getStatus() == SubmissionStatus.PENDING) {
				Thread.sleep(SubmitSolutionTask.PULL_DELAY);
				submission = Submissions.get(submission);
			}
			
			progressIndicator.setFraction(1.0);
			progressIndicator.setText("Evaluation completed");
			
			if (submission.getStatus() == SubmissionStatus.CORRECT) {
				NotificationReporter.info(String.format("Solution to %s was correct!", submission.getExercise()));
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
