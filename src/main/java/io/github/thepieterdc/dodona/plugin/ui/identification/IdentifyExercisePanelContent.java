/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui.identification;

import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.ui.AsyncProcessIcon;
import io.github.thepieterdc.dodona.plugin.ui.resources.course.CourseComboBox;
import io.github.thepieterdc.dodona.plugin.ui.resources.exercise.ExercisesList;
import io.github.thepieterdc.dodona.plugin.ui.resources.series.SeriesComboBox;
import io.github.thepieterdc.dodona.plugin.ui.util.PanelUtils;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * Panel that contains comboboxes to identify an exercise.
 */
public class IdentifyExercisePanelContent extends JPanel {
	@NonNls
	private static final String SELECT_COURSES = "SELECT_COURSES";
	@NonNls
	private static final String SELECT_EXERCISES = "SELECT_EXERCISES";
	@NonNls
	private static final String SELECT_SERIES = "SELECT_SERIES";
	@NonNls
	private static final String SELECT_LOADING = "SELECT_LOADING";
	
	private final AsyncProcessIcon loadingIcon;
	
	private final CourseComboBox courseComboBox;
	private JPanel courseSelectionPanel;
	
	private final ExercisesList exercisesList;
	private JPanel exerciseSelectionPanel;
	
	private final SeriesComboBox seriesComboBox;
	private JPanel seriesSelectionPanel;
	
	private JPanel rootPanel;
	
	/**
	 * SelectExerciseDialog constructor.
	 *
	 * @param listener     selection listener
	 * @param confirmation confirmation listener
	 */
	public IdentifyExercisePanelContent(final IdentificationListener listener,
	                                    final Runnable confirmation) {
		super(new BorderLayout());
		this.courseComboBox = new CourseComboBox(listener::onCourseSelected);
		this.exercisesList = new ExercisesList(confirmation, listener::onExerciseSelected);
		this.loadingIcon = new AsyncProcessIcon(this.getClass() + ".loading");
		this.seriesComboBox = new SeriesComboBox(listener::onSeriesSelected);
		this.initialize();
	}
	
	/**
	 * Creates a small loading panel.
	 *
	 * @return the loading panel
	 */
	@Nonnull
	private JComponent createLoadingPanel() {
		final JPanel loadingPanel = new JPanel(new BorderLayout());
		loadingPanel.add(this.loadingIcon, BorderLayout.CENTER);
		return ScrollPaneFactory.createScrollPane(loadingPanel, true);
	}
	
	/**
	 * Gets the course selection box.
	 *
	 * @return the course combo box
	 */
	@Nonnull
	CourseComboBox getCourseComboBox() {
		return this.courseComboBox;
	}
	
	/**
	 * Gets the list of exercises.
	 *
	 * @return the exercises list
	 */
	@Nonnull
	ExercisesList getExercisesList() {
		return this.exercisesList;
	}
	
	/**
	 * Gets the series selection box.
	 *
	 * @return the series combo box
	 */
	@Nonnull
	SeriesComboBox getSeriesComboBox() {
		return this.seriesComboBox;
	}
	
	/**
	 * Initializes the UI.
	 */
	private void initialize() {
		// Initialize the courses.
		this.courseSelectionPanel.add(this.courseComboBox, SELECT_COURSES);
		this.courseSelectionPanel.add(this.createLoadingPanel(), SELECT_LOADING);

		// Initialize the exercises.
		this.exerciseSelectionPanel.add(
			ScrollPaneFactory.createScrollPane(this.exercisesList),
			SELECT_EXERCISES
		);
		this.exerciseSelectionPanel.add(this.createLoadingPanel(), SELECT_LOADING);

		// Initialize the series.
		this.seriesSelectionPanel.add(this.seriesComboBox, SELECT_SERIES);
		this.seriesSelectionPanel.add(this.createLoadingPanel(), SELECT_LOADING);

		this.add(this.rootPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Sets the loading status of the courses.
	 *
	 * @param loading the loading status
	 */
	public void setCoursesLoading(final boolean loading) {
		final String card = loading ? SELECT_LOADING : SELECT_COURSES;
		PanelUtils.showCard(this.courseSelectionPanel, card);
	}
	
	/**
	 * Sets the loading status of the exercises.
	 *
	 * @param loading the loading status
	 */
	public void setExercisesLoading(final boolean loading) {
		final String card = loading ? SELECT_LOADING : SELECT_EXERCISES;
		PanelUtils.showCard(this.exerciseSelectionPanel, card);
	}
	
	/**
	 * Sets the loading status of the series.
	 *
	 * @param loading the loading status
	 */
	public void setSeriesLoading(final boolean loading) {
		final String card = loading ? SELECT_LOADING : SELECT_SERIES;
		PanelUtils.showCard(this.seriesSelectionPanel, card);
	}
//
//	/**
//	 * Handler for selected series.
//	 *
//	 * @param series the selected series
//	 */
//	private void onSeriesSelected(@Nullable final Series series) {
//		// Disable the exercise selection and clear the options.
//		this.exercisesList.setEnabled(false);
//		this.exercisesList.setResources(Collections.emptyList());
//
//		// Disable the OK action.
//		this.setOKActionEnabled(false);
//
//		// No series was selected.
//		if (series == null) {
//			return;
//		}
//
//		// Load the exercises.
//		this.updateExercises(series).thenRun(() ->
//			this.exercisesList.setEnabled(true)
//		);
//	}
}
