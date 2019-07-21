/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.selection;

import be.ugent.piedcler.dodona.plugin.Icons;
import io.github.thepieterdc.dodona.data.ExerciseStatus;
import io.github.thepieterdc.dodona.resources.Exercise;

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
			if (exercise.getStatus() == ExerciseStatus.CORRECT) {
				this.setIcon(Icons.CORRECT);
			} else if(exercise.getStatus() != ExerciseStatus.NOT_ATTEMPTED) {
				this.setIcon(Icons.INCORRECT);
			}
		}
		return this;
	}
}
