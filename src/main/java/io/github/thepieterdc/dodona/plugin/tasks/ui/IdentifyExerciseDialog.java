/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.tasks.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.ui.AsyncProcessIcon;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.ui.resources.course.CourseComboBox;
import io.github.thepieterdc.dodona.plugin.ui.resources.exercise.ExercisesList;
import io.github.thepieterdc.dodona.plugin.ui.resources.series.SeriesComboBox;
import io.github.thepieterdc.dodona.plugin.ui.util.PanelUtils;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Series;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Controller dialog that prompts the user to identify an exercise.
 */
public class IdentifyExerciseDialog extends DialogWrapper {
	@NonNls
	private static final String CARD_COURSES = "SELECT_COURSES";
	@NonNls
	private static final String CARD_EXERCISES = "SELECT_EXERCISES";
	@NonNls
	private static final String CARD_SERIES = "SELECT_SERIES";
	@NonNls
	private static final String CARD_LOADING = "SELECT_LOADING";
	
	private static final int HEIGHT = 250;
	private static final int WIDTH = 400;
	
	private final DodonaExecutorHolder executor;
	private final Project project;
	
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
	 * @param project  the current project
	 * @param executor request executor
	 */
	public IdentifyExerciseDialog(final Project project,
	                              final DodonaExecutorHolder executor) {
		super(project, true);
		this.courseComboBox = new CourseComboBox(this::onCourseSelected);
		this.executor = executor;
		this.exercisesList = new ExercisesList(
			this::doOKAction,
			item -> this.setOKActionEnabled(true)
		);
		this.loadingIcon = new AsyncProcessIcon(this.getClass() + ".loading");
		this.project = project;
		this.rootPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.seriesComboBox = new SeriesComboBox(this::onSeriesSelected);
		
		this.setOKActionEnabled(false);
		this.setTitle(DodonaBundle.message("dialog.select_exercise.title"));
		this.init();
		
		this.initialize();
	}
	
	@Override
	protected JComponent createCenterPanel() {
		return this.rootPanel;
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
		return this.courseComboBox.getSelectedResource().flatMap(course ->
			this.seriesComboBox.getSelectedResource().flatMap(series ->
				this.exercisesList.getSelectedResource().map(exercise ->
					new FullIdentification(course, series, exercise)
				)
			)
		);
	}
	
	/**
	 * Initializes the UI.
	 */
	private void initialize() {
		// Initialize the courses.
		this.courseSelectionPanel.add(this.courseComboBox, CARD_COURSES);
		this.courseSelectionPanel.add(this.createLoadingPanel(), CARD_LOADING);
		
		// Initialize the exercises.
		this.exerciseSelectionPanel.add(
			ScrollPaneFactory.createScrollPane(this.exercisesList),
			CARD_EXERCISES
		);
		this.exerciseSelectionPanel.add(this.createLoadingPanel(), CARD_LOADING);
		this.exerciseSelectionPanel.setEnabled(false);
		
		// Initialize the series.
		this.seriesSelectionPanel.add(this.seriesComboBox, CARD_SERIES);
		this.seriesSelectionPanel.add(this.createLoadingPanel(), CARD_LOADING);
		this.seriesComboBox.setEnabled(false);
		
		// Get the course of the project.
		final Optional<Long> optCourse = DodonaProjectSettings
			.getInstance(this.project)
			.getCourseId();
		
		// Load the courses.
		this.updateCourses().thenRun(() -> optCourse.ifPresent(id ->
			this.courseComboBox.setSelectedResource(course -> course.getId() == id)
		));
	}
	
	/**
	 * Handler for selected courses.
	 *
	 * @param course the selected course
	 */
	private void onCourseSelected(@Nullable final Course course) {
		// Disable the series selection and clear the options.
		this.seriesComboBox.setEnabled(false);
		this.seriesComboBox.setResources(Collections.emptyList());
		
		// Disable the exercise selection and clear the options.
		this.exercisesList.setEnabled(false);
		this.exercisesList.setResources(Collections.emptyList());
		
		// Disable the OK action.
		this.setOKActionEnabled(false);
		
		// No course was selected.
		if (course == null) {
			return;
		}
		
		// Load the series.
		this.updateSeries(course).thenRun(() ->
			this.seriesComboBox.setEnabled(true)
		);
	}
	
	/**
	 * Handler for selected series.
	 *
	 * @param series the selected series
	 */
	private void onSeriesSelected(@Nullable final Series series) {
		// Disable the exercise selection and clear the options.
		this.exercisesList.setEnabled(false);
		this.exercisesList.setResources(Collections.emptyList());
		
		// Disable the OK action.
		this.setOKActionEnabled(false);
		
		// No series was selected.
		if (series == null) {
			return;
		}
		
		// Load the exercises.
		this.updateExercises(series).thenRun(() ->
			this.exercisesList.setEnabled(true)
		);
	}
	
	/**
	 * Updates the list of courses.
	 *
	 * @return the courses
	 */
	@Nonnull
	private CompletableFuture<List<Course>> updateCourses() {
		// Show the loading cards.
		PanelUtils.showCard(this.courseSelectionPanel, CARD_LOADING);
		PanelUtils.showCard(this.seriesSelectionPanel, CARD_LOADING);
		PanelUtils.showCard(this.exerciseSelectionPanel, CARD_LOADING);
		
		// Load the courses.
		return this.executor.getExecutor()
			.execute(dodona -> dodona.me().getSubscribedCourses())
			.whenComplete((courses, error) -> SwingUtilities.invokeLater(() -> {
				this.courseComboBox.setResources(courses);
				PanelUtils.showCard(this.courseSelectionPanel, CARD_COURSES);
				
				// Focus the course panel.
				this.courseComboBox.requestFocus();
			}));
	}
	
	/**
	 * Updates the list of exercises.
	 *
	 * @return the exercises
	 */
	@Nonnull
	private CompletableFuture<List<Exercise>> updateExercises(final Series series) {
		// Show the loading card.
		PanelUtils.showCard(this.exerciseSelectionPanel, CARD_LOADING);
		
		// Load the exercises.
		return this.executor.getExecutor()
			.execute(dodona -> dodona.exercises().getAll(series))
			.whenComplete((exercises, error) -> SwingUtilities.invokeLater(() -> {
				this.exercisesList.setResources(exercises);
				this.setOKActionEnabled(this.exercisesList.getSelectedResource().isPresent());
				PanelUtils.showCard(this.exerciseSelectionPanel, CARD_EXERCISES);
			}));
	}
	
	/**
	 * Updates the list of series.
	 *
	 * @return the series
	 */
	@Nonnull
	private CompletableFuture<List<Series>> updateSeries(final Course course) {
		// Show the loading cards.
		PanelUtils.showCard(this.seriesSelectionPanel, CARD_LOADING);
		PanelUtils.showCard(this.exerciseSelectionPanel, CARD_LOADING);
		
		// Load the series.
		return this.executor.getExecutor()
			.execute(dodona -> dodona.series().getAll(course))
			.whenComplete((series, error) -> SwingUtilities.invokeLater(() -> {
				this.seriesComboBox.setResources(series);
				PanelUtils.showCard(this.seriesSelectionPanel, CARD_SERIES);
			}));
	}
}
