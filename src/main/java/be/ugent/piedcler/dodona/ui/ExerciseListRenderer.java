/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.ui;

import be.ugent.piedcler.dodona.Icons;
import be.ugent.piedcler.dodona.dto.Exercise;

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
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value instanceof Exercise) {
			final Exercise exercise = (Exercise) value;
			this.setText(exercise.getName());
			if (exercise.getLastSolutionCorrect() && exercise.getHasCorrectSolution()) {
				this.setIcon(Icons.CORRECT);
			} else if (!exercise.getLastSolutionCorrect()) {
				this.setIcon(Icons.INCORRECT);
			}
		}
		return this;
	}
}
