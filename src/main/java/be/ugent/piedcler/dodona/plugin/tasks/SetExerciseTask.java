/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.exceptions.DodonaException;
import be.ugent.piedcler.dodona.plugin.Api;
import be.ugent.piedcler.dodona.plugin.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.plugin.notifications.Notifier;
import be.ugent.piedcler.dodona.plugin.ui.CourseSelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.ExerciseSelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.SelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.SeriesSelectionDialog;
import be.ugent.piedcler.dodona.resources.Course;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
	 * Shows a selection dialog.
	 *
	 * @param dialog the dialog to show
	 * @param title  title of the dialog
	 * @param <T>    type class of the options
	 * @return the chosen element, or null if canceled
	 */
	@Nullable
	private static <T> T choose(final String title, final SelectionDialog<T> dialog) {
		final DialogBuilder coursesBuilder = new DialogBuilder();
		coursesBuilder.setCenterPanel(dialog.getRootPane());
		coursesBuilder.setTitle(title);
		coursesBuilder.removeAllActions();
		coursesBuilder.addOkAction();
		coursesBuilder.addCancelAction();
		
		if (coursesBuilder.show() == DialogWrapper.OK_EXIT_CODE) {
			return dialog.getSelectedItem();
		}
		return null;
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progressIndicator) {
		try {
			final List<Course> myCourses = Api.callModal(this.myProject, "Retrieving courses",
				dodona -> dodona.me().getSubscribedCourses()
			);
			
			progressIndicator.setFraction(0.30);
			progressIndicator.setText("Waiting for course selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedCourse = choose("Select Course", new CourseSelectionDialog(myCourses)));
			
			if (this.selectedCourse == null) return;
			
			final List<Series> courseSeries = Api.callModal(this.myProject, "Retrieving series",
				dodona -> dodona.series().getAll(this.selectedCourse)
			);
			
			progressIndicator.setFraction(0.60);
			progressIndicator.setText("Waiting for series selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedSeries = choose("Select Series", new SeriesSelectionDialog(courseSeries)));
			
			if (this.selectedSeries == null) return;
			
			final List<Exercise> exercises = Api.callModal(this.myProject, "Retrieving exercises",
				dodona -> dodona.exercises().getAll(this.selectedSeries)
			);
			
			progressIndicator.setFraction(0.90);
			progressIndicator.setText("Waiting for exercise selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedExercise = choose("Select Exercise", new ExerciseSelectionDialog(exercises)));
			
			if (this.selectedExercise == null) return;
			
			progressIndicator.setFraction(0.95);
			progressIndicator.setText("Setting exercise...");
			
			this.identifierSetter.accept(this.selectedExercise.getUrl());
		} catch (final ErrorMessageException | InvocationTargetException | IOException | DodonaException error) {
			Notifier.error(this.myProject, "Exercise not set.", error.getMessage());
		} catch (final InterruptedException ex) {
			// aborted by user.
		}
	}
}
