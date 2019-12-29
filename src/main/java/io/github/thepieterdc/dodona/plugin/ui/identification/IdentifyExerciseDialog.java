/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui.identification;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Disposer;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Series;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * Controller dialog that prompts the user to identify an exercise.
 */
public final class IdentifyExerciseDialog extends DialogWrapper implements IdentificationListener {
	private static final int HEIGHT = 250;
	private static final int WIDTH = 400;
	
	@Nullable
	private Course selectedCourse;
	@Nullable
	private Exercise selectedExercise;
	@Nullable
	private Series selectedSeries;
	
	private final IdentifyExercisePanel root;
	
	/**
	 * IdentifyExerciseDialog constructor.
	 *
	 * @param project  the current project
	 * @param executor request executor
	 */
	public IdentifyExerciseDialog(final Project project,
	                              final DodonaExecutorHolder executor) {
		super(project, true);
		this.root = new IdentifyExercisePanel(project, executor, this, this::doOKAction);
		this.root.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setTitle(DodonaBundle.message("dialog.select_exercise.title"));
		this.verifyOKAction();
		this.init();
	}
	
	@Override
	protected JComponent createCenterPanel() {
		return this.root;
	}
	
	@Override
	protected void dispose() {
		Disposer.dispose(this.root);
		super.dispose();
	}
	
	@Nullable
	@Override
	public JComponent getPreferredFocusedComponent() {
		return this.getButton(this.getCancelAction());
	}
	
	/**
	 * Gets the selected exercise.
	 *
	 * @return the exercise if selected
	 */
	@Nonnull
	public Optional<FullIdentification> getSelectedExercise() {
		return Optional.ofNullable(this.selectedCourse).flatMap(course ->
			Optional.ofNullable(this.selectedSeries).flatMap(series ->
				Optional.ofNullable(this.selectedExercise).map(exercise ->
					new FullIdentification(course, series, exercise)
				)
			)
		);
	}
	
	@Override
	public void onCourseSelected(@Nullable final Course selected) {
		this.selectedCourse = selected;
		this.verifyOKAction();
	}
	
	@Override
	public void onExerciseSelected(@Nullable final Exercise selected) {
		this.selectedExercise = selected;
		this.verifyOKAction();
	}
	
	@Override
	public void onSeriesSelected(@Nullable final Series selected) {
		this.selectedSeries = selected;
		this.verifyOKAction();
	}
	
	/**
	 * Sets the OK action button state.
	 */
	private void verifyOKAction() {
		this.setOKActionEnabled(this.selectedCourse != null
			&& this.selectedExercise != null
			&& this.selectedSeries != null
		);
	}
}
