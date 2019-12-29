/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui.identification;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.api.executor.ExecutorListener;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.ui.cards.NoConnectionCard;
import io.github.thepieterdc.dodona.plugin.ui.panels.ContentPanelBase;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Series;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Collections;
import java.util.Optional;

/**
 * Controller panel for exercise identification.
 */
final class IdentifyExercisePanel extends ContentPanelBase<IdentifyExercisePanelContent>
	implements Disposable, IdentificationListener {
	private final DodonaExecutorHolder executor;
	
	private final IdentifyExercisePanelContent content;
	
	private final IdentificationListener listener;
	
	/**
	 * IdentifyExercisePanel constructor.
	 *
	 * @param project      the current project
	 * @param executor     request executor
	 * @param listener     listener to call upon selection
	 * @param confirmation handler to call upon confirmation
	 */
	IdentifyExercisePanel(final Project project,
	                      final DodonaExecutorHolder executor,
	                      final IdentificationListener listener,
	                      final Runnable confirmation) {
		super(project);
		this.content = new IdentifyExercisePanelContent(this, confirmation);
		this.executor = executor;
		this.listener = listener;
		this.initialize();
		
		// Refresh the contents when the default executor is updated.
		ApplicationManager.getApplication().getMessageBus()
			.connect(this)
			.subscribe(ExecutorListener.UPDATED_TOPIC, this::updateCourses);
	}
	
	@Nonnull
	@Override
	protected IdentifyExercisePanelContent createContentCard() {
		return this.content;
	}
	
	@Nonnull
	@Override
	protected NoConnectionCard createNoConnectionCard() {
		return NoConnectionCard.create(this::updateCourses);
	}
	
	@Override
	public void dispose() {
		// Required to dispose the message bus when this panel is no longer
		// visible.
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.updateCourses();
	}
	
	@Override
	public void onCourseSelected(@Nullable final Course course) {
		// Inform the parent window.
		this.listener.onCourseSelected(course);
		
		// Disable the series selection and clear the options.
		this.content.getSeriesComboBox().setEnabled(false);
		this.content.getSeriesComboBox().setResources(Collections.emptyList());
		
		// Disable the exercise selection and clear the options.
		this.content.getExercisesList().setEnabled(false);
		this.content.getExercisesList().setResources(Collections.emptyList());
		
		// No course was selected.
		if (course == null) {
			return;
		}
		
		// Load the series.
		this.updateSeries(course);
	}
	
	@Override
	public void onExerciseSelected(@Nullable final Exercise selected) {
		this.listener.onExerciseSelected(selected);
	}
	
	@Override
	public void onSeriesSelected(@Nullable final Series selected) {
		// Inform the parent window.
		this.listener.onSeriesSelected(selected);
		
		// Disable the exercise selection and clear the options.
		this.content.getExercisesList().setEnabled(false);
		this.content.getExercisesList().setResources(Collections.emptyList());
		
		// No series was selected.
		if (selected == null) {
			return;
		}
		
		// Load the exercises.
		this.updateExercises(selected);
	}
	
	/**
	 * Updates the list of courses.
	 */
	private void updateCourses() {
		// Show the content card.
		this.showContentCard();
		
		// Show the loading cards.
		this.content.setCoursesLoading(true);
		this.content.setExercisesLoading(true);
		this.content.setSeriesLoading(true);
		
		// Get the current course, if available.
		final Optional<Long> optCourse = Optional.ofNullable(this.project)
			.map(DodonaProjectSettings::getInstance)
			.flatMap(DodonaProjectSettings::getCourseId);
		
		// Load the courses.
		this.executor.getExecutor()
			.execute(dodona -> dodona.me().getSubscribedCourses())
			.whenComplete((courses, error) -> SwingUtilities.invokeLater(() -> {
				if (error == null) {
					this.content.getCourseComboBox().setEnabled(true);
					this.content.getCourseComboBox().setResources(courses);
					this.content.setCoursesLoading(false);
					this.content.getCourseComboBox().requestFocus();
				} else {
					this.handleError(error);
				}
			}))
			.thenRun(() -> optCourse.ifPresent(courseId ->
				this.content.getCourseComboBox().setSelectedResource(courseId)
			));
	}
	
	/**
	 * Updates the list of exercises.
	 *
	 * @param series the selected series
	 */
	private void updateExercises(final Series series) {
		// Show the loading card.
		this.content.setExercisesLoading(true);
		
		// Load the exercises.
		this.executor.getExecutor()
			.execute(dodona -> dodona.exercises().getAll(series))
			.whenComplete((exercises, error) -> SwingUtilities.invokeLater(() -> {
				if (error == null) {
					this.content.getExercisesList().setEnabled(true);
					this.content.getExercisesList().setResources(exercises);
					this.content.setExercisesLoading(false);
					this.content.getExercisesList().requestFocus();
				} else {
					this.handleError(error);
				}
			}));
	}
	
	/**
	 * Updates the list of series.
	 *
	 * @param course the selected course
	 */
	private void updateSeries(final Course course) {
		// Show the loading cards.
		this.content.setExercisesLoading(true);
		this.content.setSeriesLoading(true);
		
		// Load the series.
		this.executor.getExecutor()
			.execute(dodona -> dodona.series().getAll(course))
			.whenComplete((series, error) -> SwingUtilities.invokeLater(() -> {
				if (error == null) {
					this.content.getSeriesComboBox().setEnabled(true);
					this.content.getSeriesComboBox().setResources(series);
					this.content.setSeriesLoading(false);
					this.content.getSeriesComboBox().requestFocus();
				} else {
					this.handleError(error);
				}
			}));
	}
}
