/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.exceptions.DodonaException;
import be.ugent.piedcler.dodona.plugin.Api;
import be.ugent.piedcler.dodona.plugin.exceptions.UserAbortedException;
import be.ugent.piedcler.dodona.plugin.ui.selection.CourseSelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.selection.ExerciseSelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.selection.SelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.selection.SeriesSelectionDialog;
import be.ugent.piedcler.dodona.resources.Course;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Requests the user to select a course, series and exercise.
 */
public class SelectExerciseTask extends Task.WithResult<Optional<Exercise>, RuntimeException> {
	private Course selectedCourse;
	private Exercise selectedExercise;
	private Series selectedSeries;
	
	/**
	 * SelectExerciseTask constructor.
	 *
	 * @param project the project to display notifications in
	 */
	public SelectExerciseTask(@Nonnull final Project project) {
		super(project, "Configure Exercise", false);
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
	private <T> T choose(final String title, final SelectionDialog<T> dialog) {
		final DialogBuilder builder = new DialogBuilder(this.myProject);
		builder.setCenterPanel(dialog.getRootPane());
		builder.setTitle(title);
		builder.removeAllActions();
		
		if (dialog.hasItems()) {
			builder.addOkAction();
			builder.addCancelAction();
			builder.setOkActionEnabled(false);
			dialog.addListener(i -> builder.setOkActionEnabled(i != null));
		} else {
			builder.addCloseButton();
		}
		
		if (builder.show() == DialogWrapper.OK_EXIT_CODE) {
			return dialog.getSelectedItem();
		}
		return null;
	}
	
	@Override
	protected Optional<Exercise> compute(@NotNull ProgressIndicator progressIndicator) throws RuntimeException {
		try {
			final List<Course> myCourses = Api.callModal(this.myProject, "Retrieving courses",
				dodona -> dodona.me().getSubscribedCourses()
			);
			
			progressIndicator.setFraction(0.30);
			progressIndicator.setText("Waiting for course selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedCourse = choose("Select Course", new CourseSelectionDialog(myCourses)));
			
			if (this.selectedCourse == null) return Optional.empty();
			
			final List<Series> courseSeries = Api.callModal(this.myProject, "Retrieving series",
				dodona -> dodona.series().getAll(this.selectedCourse)
			);
			
			progressIndicator.setFraction(0.60);
			progressIndicator.setText("Waiting for series selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedSeries = choose("Select Series", new SeriesSelectionDialog(courseSeries)));
			
			if (this.selectedSeries == null) return Optional.empty();
			
			final List<Exercise> exercises = Api.callModal(this.myProject, "Retrieving exercises",
				dodona -> dodona.exercises().getAll(this.selectedSeries)
			);
			
			progressIndicator.setFraction(0.90);
			progressIndicator.setText("Waiting for exercise selection...");
			
			EventQueue.invokeAndWait(() -> this.selectedExercise = choose("Select Exercise", new ExerciseSelectionDialog(exercises)));
			
			if (this.selectedExercise == null) return Optional.empty();
			
			return Optional.of(this.selectedExercise);
		} catch (final UserAbortedException | InterruptedException ex) {
			return Optional.empty();
		} catch (final InvocationTargetException | IOException | DodonaException error) {
			throw new RuntimeException(error);
		}
	}
}
