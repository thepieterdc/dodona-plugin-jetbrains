/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.ui;

import be.ugent.piedcler.dodona.dto.Exercise;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Renders the course name correctly in a list of Exercises.
 */
public class ExerciseListRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 4470256123408914611L;
	private static final Icon CORRECT_ICON = IconLoader.findIcon("/icons/correct.png");
	private static final Icon INCORRECT_ICON = IconLoader.findIcon("/icons/incorrect.png");


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
				this.setIcon(CORRECT_ICON);
			} else if (!exercise.getLastSolutionCorrect()) {
				this.setIcon(INCORRECT_ICON);
			}
		}
		return this;
	}
}
