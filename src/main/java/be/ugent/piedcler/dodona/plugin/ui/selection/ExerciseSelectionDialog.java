/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.selection;

import be.ugent.piedcler.dodona.plugin.settings.DodonaSettings;
import be.ugent.piedcler.dodona.plugin.ui.listeners.SelectedItemListener;
import be.ugent.piedcler.dodona.plugin.util.observable.ObservableValue;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import io.github.thepieterdc.dodona.data.ExerciseStatus;
import io.github.thepieterdc.dodona.resources.Exercise;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A dialog that allows the user to select an exercise.
 */
public class ExerciseSelectionDialog extends SelectionDialog<Exercise> {
	private JPanel contentPane;
	
	private JBList<Exercise> exercisesList;
	private final CollectionListModel<Exercise> exercisesModel;
	
	private final boolean hasItems;
	
	private JCheckBox hideCorrectCheck;
	
	private final ObservableValue<Exercise> selectedExercise;
	
	/**
	 * ExerciseSelectionDialog constructor.
	 *
	 * @param exercises the exercises to select from
	 */
	public ExerciseSelectionDialog(@Nonnull final Collection<Exercise> exercises) {
		this.exercisesModel = new CollectionListModel<>(exercises);
		this.hasItems = !exercises.isEmpty();
		this.selectedExercise = new ObservableValue<>(null);
		
		this.setContentPane(this.contentPane);
		this.setModal(true);
		
		final DodonaSettings settings = DodonaSettings.getInstance();
		
		this.exercisesList.addListSelectionListener(e -> this.selectedExercise.setValue(this.exercisesList.getSelectedValue()));
		this.exercisesList.setCellRenderer(new ExerciseListRenderer());
		this.exercisesList.setEmptyText("No exercises were found in this series.");
		this.exercisesList.setModel(this.exercisesModel);
		this.exercisesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.hideCorrectCheck.setEnabled(!exercises.isEmpty());
		this.hideCorrectCheck.addItemListener(e -> {
			final boolean hide = this.hideCorrectCheck.isSelected();
			settings.setHideCorrectExercises(hide);
			final Exercise selected = this.selectedExercise.getValue();
			this.exercisesModel.replaceAll(new ArrayList<>(hide ? getIncorrectExercises(exercises) : exercises));
			this.exercisesList.setSelectedValue(selected, true);
		});
		
		this.hideCorrectCheck.setSelected(settings.hideCorrectExercises());
		
		if (exercises.isEmpty()) {
			this.contentPane.setPreferredSize(new Dimension(250, -1));
		} else {
			this.contentPane.setPreferredSize(new Dimension(450, 300));
		}
	}
	
	@Override
	public void addListener(@Nonnull final SelectedItemListener<Exercise> listener) {
		this.selectedExercise.addListener(listener::onItemSelected);
	}
	
	/**
	 * Gets all incorrect exercises.
	 *
	 * @param all all exercises
	 * @return all incorrect exercises
	 */
	@Nonnull
	private static List<Exercise> getIncorrectExercises(@Nonnull final Collection<Exercise> all) {
		return all.stream()
			.filter(e -> e.getStatus() != ExerciseStatus.CORRECT)
			.collect(Collectors.toList());
	}
	
	@Nullable
	@Override
	public Exercise getSelectedItem() {
		return this.selectedExercise.getValue();
	}
	
	@Override
	public boolean hasItems() {
		return this.hasItems;
	}
}