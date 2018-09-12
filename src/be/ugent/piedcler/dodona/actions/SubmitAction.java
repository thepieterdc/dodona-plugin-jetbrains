/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.actions;

import be.ugent.piedcler.dodona.code.ExerciseIdentifier;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.exceptions.warnings.ExerciseNotSetException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.tasks.SetExerciseTask;
import be.ugent.piedcler.dodona.tasks.SubmitSolutionTask;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.annotations.NotNull;

/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {
	@Override
	public void actionPerformed(@NotNull final AnActionEvent event) {
		final String code = event.getData(PlatformDataKeys.FILE_TEXT);
		
		try {
			if (code != null) {
				final Exercise exercise = ExerciseIdentifier.identify(code).orElseThrow(ExerciseNotSetException::new);
				ProgressManager.getInstance().run(new SubmitSolutionTask(event.getProject(), exercise, code));
			} else {
				throw new CodeReadException();
			}
		} catch (final ExerciseNotSetException exception) {
			NotificationReporter.warning(exception.getMessage());
			ProgressManager.getInstance().run(new SetExerciseTask(event.getProject()));
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		}
	}
}
