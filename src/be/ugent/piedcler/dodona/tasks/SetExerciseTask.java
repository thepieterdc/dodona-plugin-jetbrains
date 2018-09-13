/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.tasks;

import be.ugent.piedcler.dodona.api.Courses;
import be.ugent.piedcler.dodona.api.Exercises;
import be.ugent.piedcler.dodona.dto.course.Course;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.ui.SelectCourseDialog;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Sets the course and exercise id in the header of the code.
 */
public class SetExerciseTask extends Task.Backgroundable {
	/**
	 * SetExerciseTask constructor.
	 *
	 * @param project the project to display notifications in
	 */
	public SetExerciseTask(final Project project) {
		super(project, "Configure Exercise.");
	}
	
	/**
	 * Asks the user about the course.
	 *
	 * @return the course
	 */
	@Nullable
	private static Course askCourse(final Collection<Course> courses) {
		final SelectCourseDialog selectCourseDialog = new SelectCourseDialog(courses);
		final DialogBuilder coursesBuilder = new DialogBuilder();
		coursesBuilder.setCenterPanel(selectCourseDialog.getRootPane());
		coursesBuilder.setTitle("Select Course");
		coursesBuilder.removeAllActions();
		coursesBuilder.addOkAction();
		coursesBuilder.addCancelAction();
		
		if (coursesBuilder.show() == DialogWrapper.OK_EXIT_CODE) {
			return selectCourseDialog.getSelectedCourse();
		}
		return null;
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {
		try {
			progressIndicator.setFraction(0.10);
			progressIndicator.setText("Retrieving courses...");
			
			final Collection<Course> courses = Courses.getAllSubscribed();
			
			progressIndicator.setFraction(0.40);
			progressIndicator.setText("Waiting for course selection...");
			
			final Course selectedCourse = SetExerciseTask.askCourse(courses);
			
			progressIndicator.setFraction(0.60);
			progressIndicator.setText("Retrieving exercises...");
			
			final Collection<Exercise> exercises = Exercises.getAll(selectedCourse);
			
			progressIndicator.setFraction(0.80);
			progressIndicator.setText("Waiting for exercise selection...");
			
			// Modify the code
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		}
	}
}
