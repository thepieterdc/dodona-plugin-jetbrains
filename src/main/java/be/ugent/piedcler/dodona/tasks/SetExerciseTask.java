/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.tasks;

import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.services.CourseService;
import be.ugent.piedcler.dodona.services.SeriesService;
import be.ugent.piedcler.dodona.ui.SelectCourseDialog;
import be.ugent.piedcler.dodona.ui.SelectExerciseDialog;
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

/**
 * Sets the course and exercise id in the header of the code.
 */
public class SetExerciseTask extends Task.Backgroundable {
	private final CourseService courses;
	private final SeriesService series;
	
	private Course selectedCourse;
	private Exercise selectedExercise;
	private Series selectedSeries;
	
	/**
	 * SetExerciseTask constructor.
	 *
	 * @param project the project to display notifications in
	 */
	public SetExerciseTask(final Project project) {
		super(project, "Configure Exercise.");
		this.courses = CourseService.getInstance();
		this.series = SeriesService.getInstance();
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
	 * Asks the user about the exercise.
	 *
	 * @param exercises all exercises
	 * @return the selected exercise
	 */
	@Nullable
	private static Exercise askExercise(final Collection<Exercise> exercises) {
		final SelectExerciseDialog selectExerciseDialog = new SelectExerciseDialog(exercises);
		final DialogBuilder coursesBuilder = new DialogBuilder();
		coursesBuilder.setCenterPanel(selectExerciseDialog.getRootPane());
		coursesBuilder.setTitle("Select Exercise");
		coursesBuilder.removeAllActions();
		coursesBuilder.addOkAction();
		coursesBuilder.addCancelAction();
		
		if (coursesBuilder.show() == DialogWrapper.OK_EXIT_CODE) {
			return selectExerciseDialog.getSelectedExercise();
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
			
			final Collection<Course> myCourses = this.courses.getSubscribed();
			
			progressIndicator.setFraction(0.15);
			progressIndicator.setText("Waiting for course selection...");
			
			EventQueue.invokeAndWait(() -> this.setCourse(SetExerciseTask.askCourse(myCourses)));
			
			if (this.selectedCourse == null) return;
			
			progressIndicator.setFraction(0.30);
			progressIndicator.setText("Retrieving series...");
			
			final Collection<Series> courseSeries = this.courses.get(this.selectedCourse.getId()).getSeries();
			
			progressIndicator.setFraction(0.45);
			progressIndicator.setText("Waiting for series selection...");
			
			EventQueue.invokeAndWait(() -> this.setSeries(SetExerciseTask.askSeries(courseSeries)));
			
			if (this.selectedSeries == null) return;
			
			progressIndicator.setFraction(0.60);
			progressIndicator.setText("Retrieving exercises...");
			
			final Collection<Exercise> exercises = this.series.get(this.selectedSeries.getId()).getExercises();
			
			progressIndicator.setFraction(0.75);
			progressIndicator.setText("Waiting for exercise selection...");
			
			EventQueue.invokeAndWait(() -> this.setExercise(SetExerciseTask.askExercise(exercises)));
			
			if (this.selectedExercise == null) return;
			
			progressIndicator.setFraction(0.90);
			progressIndicator.setText("Setting exercise...");
			
			// Modify the code.
			//TODO issue 4: set selectedCourse and selectedExercise in the code like
			//TODO          // Dodona course: i, exercise: j   (make sure the comments
			//TODO          // work across all languages (Python/Ruby/..)
			EventQueue.invokeLater(() -> NotificationReporter.info(String.format(
				"TODO issue #4. Course = %d, Exercise = %d",
				this.selectedCourse.getId(),
				this.selectedExercise.getId()
			)));
			
			// TODO when the above todo is implemented, uncomment this (it's a bit confusing
			// TODO to show this when it's not yet implemented)
//			EventQueue.invokeLater(() -> NotificationReporter.info("Exercise successfully set."));
		} catch (final WarningMessageException warning) {
			EventQueue.invokeLater(() -> NotificationReporter.warning(warning.getMessage()));
		} catch (final ErrorMessageException | InvocationTargetException error) {
			EventQueue.invokeLater(() -> NotificationReporter.error(error.getMessage()));
		} catch (final InterruptedException ex) {
			// aborted by user.
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
	 * Sets the selected exercise.
	 *
	 * @param exercise the exercise to set
	 */
	private void setExercise(@Nullable final Exercise exercise) {
		this.selectedExercise = exercise;
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
