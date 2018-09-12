/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.actions;

import be.ugent.piedcler.dodona.api.SubmitExercise;
import be.ugent.piedcler.dodona.dto.course.Course;
import be.ugent.piedcler.dodona.dto.course.UnknownCourse;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import be.ugent.piedcler.dodona.dto.exercise.UnknownExercise;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {
	
	@Override
	public void actionPerformed(final AnActionEvent anActionEvent) {
		final String code = anActionEvent.getData(PlatformDataKeys.FILE_TEXT);
		
		final Course course = new UnknownCourse(58L);
		final Exercise exercise = new UnknownExercise(516928727L, course);
		SubmitExercise.submit(exercise, code);
		
		if (code != null) {
			SubmitExercise.submit(exercise, code);
		} else {
			throw new RuntimeException("Code is null");
		}
	}
}
