/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.plugin.settings.DodonaSettings;
import be.ugent.piedcler.dodona.plugin.ui.listeners.SelectedItemListener;
import be.ugent.piedcler.dodona.resources.Exercise;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
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
	
	private JCheckBox hideCorrectCheck;
	
	private final SimpleObjectProperty<Exercise> selectedExercise;
	
	/**
	 * ExerciseSelectionDialog constructor.
	 *
	 * @param exercises the exercises to select from
	 */
	public ExerciseSelectionDialog(@Nonnull final Collection<Exercise> exercises) {
		this.exercisesModel = new CollectionListModel<>(exercises);
		this.selectedExercise = new SimpleObjectProperty<>(null);
		
		this.setContentPane(this.contentPane);
		this.setModal(true);
		
		final DodonaSettings settings = DodonaSettings.getInstance();
		
		this.exercisesList.addListSelectionListener(e -> this.selectedExercise.set(this.exercisesList.getSelectedValue()));
		this.exercisesList.setCellRenderer(new ExerciseListRenderer());
		this.exercisesList.setEmptyText("No exercises were found in this series.");
		this.exercisesList.setModel(this.exercisesModel);
		this.exercisesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.hideCorrectCheck.setEnabled(!exercises.isEmpty());
		this.hideCorrectCheck.addItemListener(e -> {
			final boolean hide = this.hideCorrectCheck.isSelected();
			settings.setHideCorrectExercises(hide);
			final Exercise selected = this.selectedExercise.get();
			this.exercisesModel.replaceAll(new ArrayList<>(hide ? getIncorrectExercises(exercises) : exercises));
			this.exercisesList.setSelectedValue(selected, true);
		});
		
		this.hideCorrectCheck.setSelected(settings.hideCorrectExercises());
	}
	
	@Override
	public void addListener(@Nonnull final SelectedItemListener<Exercise> listener) {
		this.selectedExercise.addListener((o, od, nw) -> listener.onItemSelected(nw));
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
			.filter(e -> !(e.isLastSolutionCorrect() && e.hasCorrectSolution()))
			.collect(Collectors.toList());
	}
	
	@Nullable
	@Override
	public Exercise getSelectedItem() {
		return this.selectedExercise.get();
	}
	
	@Override
	public boolean hasItems() {
		return !this.exercisesList.isEmpty();
	}
}