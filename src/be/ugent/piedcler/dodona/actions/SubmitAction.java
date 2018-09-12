/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.actions;

import be.ugent.piedcler.dodona.dto.course.Course;
import be.ugent.piedcler.dodona.dto.course.UnknownCourse;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import be.ugent.piedcler.dodona.dto.exercise.UnknownExercise;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.tasks.SubmitSolutionTask;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressManager;

/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {
	@Override
	public void actionPerformed(final AnActionEvent event) {
		final String code = event.getData(PlatformDataKeys.FILE_TEXT);
		
		final Course course = new UnknownCourse(58L);
		final Exercise exercise = new UnknownExercise(516928727L, course);
		
		try {
			if (code != null) {
				ProgressManager.getInstance().run(new SubmitSolutionTask(event.getProject(), exercise, code));
			} else {
				throw new CodeReadException();
			}
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		}
	}
}
