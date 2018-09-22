/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.ui;

import be.ugent.piedcler.dodona.dto.Exercise;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

/**
 * A dialog that allows the user to select an exercise.
 */
public class SelectExerciseDialog extends JDialog {
	private JPanel contentPane;
	private JBList<Exercise> exercisesList;
	
	@Nullable
	private Exercise selectedExercise;
	
	/**
	 * SelectExerciseDialog constructor.
	 *
	 * @param exercises the exercises to select from
	 */
	public SelectExerciseDialog(final Collection<Exercise> exercises) {
		this.createComponents();
		this.exercisesList.addListSelectionListener(e -> this.selectedExercise = this.exercisesList.getSelectedValue());
		this.exercisesList.setCellRenderer(new ExerciseListRenderer());
		this.exercisesList.setEmptyText("No exercises were found in this series.");
		this.exercisesList.setModel(new CollectionListModel<>(exercises));
		this.exercisesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**
	 * Creates the form components.
	 */
	private void createComponents() {
		this.setContentPane(this.contentPane);
		this.setModal(true);
	}
	
	/**
	 * Gets the selected exercise.
	 *
	 * @return the selected exercise
	 */
	@Nullable
	public Exercise getSelectedExercise() {
		return this.selectedExercise;
	}
}