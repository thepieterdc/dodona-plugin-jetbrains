/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.data.SubmissionStatus;
import be.ugent.piedcler.dodona.exceptions.DodonaException;
import be.ugent.piedcler.dodona.plugin.Api;
import be.ugent.piedcler.dodona.plugin.dto.Solution;
import be.ugent.piedcler.dodona.plugin.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.SubmissionTimeoutException;
import be.ugent.piedcler.dodona.plugin.notifications.Notifier;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.Submission;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
		super(project, "Submitting Solution");
		this.solution = solution;
		this.presentation = presentation;
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {
		try {
			this.presentation.setEnabled(false);
			
			final long createdSubmissionId = Api.callModal(this.myProject, "Submitting solution",
				dodona -> dodona.submissions().create(
					this.solution.getCourseId().orElse(null),
					this.solution.getSeriesId().orElse(null),
					this.solution.getExerciseId(),
					this.solution.getCode()
				)
			);
			
			Submission submission = Api.call(this.myProject, dodona -> dodona.submissions().get(createdSubmissionId));
			
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
				
				submission = Api.call(this.myProject, dodona -> dodona.submissions().get(createdSubmissionId));
				
				delay = Math.min(
					(long) ((double) delay * SubmitSolutionTask.DELAY_BACKOFF_FACTOR),
					SubmitSolutionTask.DELAY_MAX
				);
				
				total += delay;
			}
			
			// Ugly hack to make the submission available in the api call.
			final Submission finalSubmission = submission;
			
			final Exercise exercise = Api.call(this.myProject, dodona -> dodona.exercises().get(finalSubmission));
			
			if ((submission.getStatus() == SubmissionStatus.RUNNING)
				|| (submission.getStatus() == SubmissionStatus.QUEUED)) {
				throw new SubmissionTimeoutException(submission, exercise);
			}
			
			progressIndicator.setFraction(1.0);
			progressIndicator.setText("Evaluation completed");
			
			if (submission.getStatus() == SubmissionStatus.CORRECT) {
				Notifier.info(this.myProject, "Solution was correct!",
					String.format("Your solution to %s has been accepted!", exercise.getName())
				);
			} else {
				Notifier.warning(this.myProject, "Incorrect solution",
					String.format("Your solution to \"%s\" was incorrect. <a href=\"%s\">More details</a>.", exercise.getName(), submission.getUrl())
				);
			}
		} catch (final WarningMessageException warning) {
			Notifier.warning(this.myProject, warning.getMessage());
		} catch (final ErrorMessageException | IOException | DodonaException error) {
			Notifier.error(this.myProject, error.getMessage());
		} catch (final InterruptedException ex) {
			throw new RuntimeException(ex);
		} finally {
			this.presentation.setEnabled(true);
		}
	}
}
