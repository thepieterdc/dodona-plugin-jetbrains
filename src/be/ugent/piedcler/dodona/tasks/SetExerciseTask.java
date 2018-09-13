/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.tasks;

import be.ugent.piedcler.dodona.api.Courses;
import be.ugent.piedcler.dodona.dto.course.Course;
import be.ugent.piedcler.dodona.dto.series.Series;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.ui.SelectCourseDialog;
import be.ugent.piedcler.dodona.ui.SelectSeriesDialog;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static be.ugent.piedcler.dodona.api.Series.getAll;

/**
 * Sets the course and exercise id in the header of the code.
 */
public class SetExerciseTask extends Task.Backgroundable {
	private Course selectedCourse;
	private Series selectedSeries;
	
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
	 * @param courses all courses
	 * @return the selected course
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
	
	/**
	 * Asks the user about the series.
	 *
	 * @param series all series
	 * @return the selected series
	 */
	@Nullable
	private static Series askSeries(final Collection<Series> series) {
		final SelectSeriesDialog selectSeriesDialog = new SelectSeriesDialog(series);
		final DialogBuilder coursesBuilder = new DialogBuilder();
		coursesBuilder.setCenterPanel(selectSeriesDialog.getRootPane());
		coursesBuilder.setTitle("Select Series");
		coursesBuilder.removeAllActions();
		coursesBuilder.addOkAction();
		coursesBuilder.addCancelAction();
		
		if (coursesBuilder.show() == DialogWrapper.OK_EXIT_CODE) {
			return selectSeriesDialog.getSelectedSeries();
		}
		return null;
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {
		try {
			progressIndicator.setFraction(0.10);
			progressIndicator.setText("Retrieving courses...");
			
			final Collection<Course> courses = Courses.getAllSubscribed();
			
			progressIndicator.setFraction(0.15);
			progressIndicator.setText("Waiting for course selection...");
			
			EventQueue.invokeAndWait(() -> this.setCourse(SetExerciseTask.askCourse(courses)));
			
			if (this.selectedCourse == null) return;
			
			progressIndicator.setFraction(0.30);
			progressIndicator.setText("Retrieving series...");
			
			final Collection<Series> series = getAll(this.selectedCourse);
			
			progressIndicator.setFraction(0.45);
			progressIndicator.setText("Waiting for series selection...");
			
			EventQueue.invokeAndWait(() -> this.setSeries(SetExerciseTask.askSeries(series)));
			
			if (this.selectedSeries == null) return;
			
			progressIndicator.setFraction(0.60);
			progressIndicator.setText("Retrieving exercises...");

//			final Collection<Exercise> exercises = Exercises.getAll(series);
//
//			progressIndicator.setFraction(0.75);
//			progressIndicator.setText("Waiting for exercise selection...");
//
//			final Exercise selectedExercise = SetExerciseTask.askExercise(exercises);
//
//			if (selectedExercise == null) return;
//
//			progressIndicator.setFraction(0.90);
//			progressIndicator.setText("Setting exercise...");
//
//			NotificationReporter.info("Exercise successfully set.");
			
			// Modify the code
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		} catch (final InterruptedException | InvocationTargetException e) {
			// ignore, user closed the dialog.
		}
	}
	
	/**
	 * Sets the selected course.
	 *
	 * @param course the course to set
	 */
	private void setCourse(@Nullable final Course course) {
		this.selectedCourse = course;
	}
	
	/**
	 * Sets the selected series.
	 *
	 * @param series the series to set
	 */
	private void setSeries(@Nullable final Series series) {
		this.selectedSeries = series;
	}
}
