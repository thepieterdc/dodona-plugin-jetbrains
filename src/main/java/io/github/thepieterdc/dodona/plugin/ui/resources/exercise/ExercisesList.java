/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.exercise;

import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import io.github.thepieterdc.dodona.data.ExerciseStatus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.listeners.ConfirmationListener;
import io.github.thepieterdc.dodona.plugin.ui.listeners.DoubleClickListener;
import io.github.thepieterdc.dodona.plugin.ui.resources.ResourceSelector;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Resource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A list containing exercises.
 */
public class ExercisesList extends JBList<Exercise> implements ResourceSelector<Exercise> {
	private static final Predicate<Exercise> isNotCorrect = exercise ->
		exercise.getStatus() == ExerciseStatus.INCORRECT
			|| exercise.getStatus() == ExerciseStatus.NOT_ATTEMPTED;
	
	private final CollectionListModel<Exercise> model;
	
	/**
	 * ExercisesList constructor.
	 *
	 * @param listener listener to call upon double click events
	 */
	public ExercisesList(final ConfirmationListener listener) {
		super();
		this.model = new CollectionListModel<>();
		// Detect double-clicks.
		this.addMouseListener((DoubleClickListener) e -> listener.onConfirmed());
		this.setEmptyText(DodonaBundle.message("dialog.select_exercise.exercise.empty"));
		this.setModel(this.model);
		this.setCellRenderer(new ExerciseNameStatusRenderer());
	}
	
	@Nonnull
	@Override
	public Optional<Exercise> getSelectedResource() {
		return Optional.ofNullable(this.getSelectedValue());
	}
	
	@Nonnull
	@Override
	public Optional<Long> getSelectedResourceId() {
		return this.getSelectedResource().map(Resource::getId);
	}
	
	@Override
	public void setResources(final Collection<? extends Exercise> resources) {
		// Change the empty text based on the current state.
		this.setEmptyText(this.isEnabled() ?
			DodonaBundle.message("dialog.select_exercise.exercise.empty") :
			DodonaBundle.message("dialog.select_exercise.exercise.select_series")
		);
		
		// Set the new items.
		this.model.replaceAll(
			resources.stream().sorted().collect(Collectors.toList())
		);
		
		// Find the first incorrect exercise and select it.
		this.model.getItems().stream()
			.filter(isNotCorrect)
			.findAny()
			.ifPresent(this::setSelectedResource);
	}
	
	@Override
	public void setSelectedResource(final Predicate<? super Exercise> predicate) {
		this.setSelectedResource(this.model.getItems().stream()
			.filter(predicate)
			.findFirst()
			.orElse(null)
		);
	}
	
	@Override
	public void setSelectedResource(@Nullable final Exercise resource) {
		this.setSelectedValue(resource, true);
	}
}
