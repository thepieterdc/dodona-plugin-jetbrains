/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.DodonaClient;
import be.ugent.piedcler.dodona.exceptions.DodonaException;
import be.ugent.piedcler.dodona.plugin.Api;
import be.ugent.piedcler.dodona.plugin.authentication.AuthenticationData;
import be.ugent.piedcler.dodona.plugin.exceptions.CancellationException;
import be.ugent.piedcler.dodona.plugin.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.plugin.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.plugin.ui.SelectCourseDialog;
import be.ugent.piedcler.dodona.plugin.ui.SelectExerciseDialog;
import be.ugent.piedcler.dodona.plugin.ui.SelectSeriesDialog;
import be.ugent.piedcler.dodona.resources.Course;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.Series;
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
import java.util.List;
import java.util.function.Consumer;

/**
 * Sets the course and exercise id in the header of the code.
 */
public class SetExerciseTask extends Task.Backgroundable {
	
	private final Consumer<String> identifierSetter;
	
	private Course selectedCourse;
	private Exercise selectedExercise;
	private Series selectedSeries;
	
	/**
	 * SetExerciseTask constructor.
	 *
	 * @param project          the project to display notifications in
	 * @param identifierSetter sets te course and exercise ids
	 */
	public SetExerciseTask(final Project project, final Consumer<String> identifierSetter) {
		super(project, "Configure Exercise.");
		this.identifierSetter = identifierSetter;
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
			
			final DodonaClient dodona = Api.getInstance();
			
			final List<Course> myCourses = Api.call(
				this.myProject,
				AuthenticationData.create("https://dodona.ugent.be", "test"),
				progressIndicator,
				client -> client.me().getSubscribedCourses()
			);
			
			progressIndicator.setFraction(0.15);
			progressIndicator.setText("Waiting for course selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedCourse = SetExerciseTask.askCourse(myCourses));
			
			if (this.selectedCourse == null) return;
			
			progressIndicator.setFraction(0.30);
			progressIndicator.setText("Retrieving series...");
			
			final List<Series> courseSeries = dodona.series().getAll(this.selectedCourse);
			
			progressIndicator.setFraction(0.45);
			progressIndicator.setText("Waiting for series selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedSeries = SetExerciseTask.askSeries(courseSeries));
			
			if (this.selectedSeries == null) return;
			
			progressIndicator.setFraction(0.60);
			progressIndicator.setText("Retrieving exercises...");
			
			final List<Exercise> exercises = dodona.exercises().getAll(this.selectedSeries);
			
			progressIndicator.setFraction(0.75);
			progressIndicator.setText("Waiting for exercise selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedExercise = SetExerciseTask.askExercise(exercises));
			
			if (this.selectedExercise == null) return;
			
			progressIndicator.setFraction(0.90);
			progressIndicator.setText("Setting exercise...");
			
			
			this.identifierSetter.accept(this.selectedExercise.getUrl());
			
			EventQueue.invokeLater(() -> NotificationReporter.info("Exercise successfully set."));
		} catch (final WarningMessageException warning) {
			EventQueue.invokeLater(() -> NotificationReporter.warning(warning.getMessage()));
		} catch (final ErrorMessageException | InvocationTargetException | DodonaException error) {
			EventQueue.invokeLater(() -> NotificationReporter.error(error.getMessage()));
		} catch (final CancellationException | InterruptedException ex) {
			// aborted by user.
		}
	}
}
