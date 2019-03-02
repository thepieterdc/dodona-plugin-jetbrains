/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.plugin.Icons;
import be.ugent.piedcler.dodona.resources.Exercise;

import javax.swing.*;
import java.awt.*;

/**
 * Renders the course name correctly in a list of Exercises.
 */
public class ExerciseListRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 4470256123408914611L;


	@Override
	public Component getListCellRendererComponent(final JList<?> list,
												  final Object value,
												  final int index,
												  final boolean isSelected,
												  final boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, false);
		if (value instanceof Exercise) {
			final Exercise exercise = (Exercise) value;
			this.setText(exercise.getName());
			if (exercise.isLastSolutionCorrect() && exercise.hasCorrectSolution()) {
				this.setIcon(Icons.CORRECT);
			}
			// TODO: Mark an exercise as incorrect when the API is updated.
			// https://github.com/thepieterdc/dodona-plugin-jetbrains/issues/65
		}
		return this;
	}
}
