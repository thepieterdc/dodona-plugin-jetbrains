/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.selection;

import be.ugent.piedcler.dodona.data.ExerciseStatus;
import be.ugent.piedcler.dodona.plugin.Icons;
import be.ugent.piedcler.dodona.resources.Exercise;
import com.intellij.ui.ColoredListCellRenderer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Renders the exercise name correctly in a list of Exercises.
 */
public class ExerciseListRenderer extends ColoredListCellRenderer<Exercise> {
	@Override
	protected void customizeCellRenderer(@NotNull JList<? extends Exercise> list,
	                                     @Nonnull final Exercise exercise,
	                                     int index,
	                                     boolean selected,
	                                     boolean hasFocus) {
		this.append(exercise.getName());
		if (exercise.getStatus() == ExerciseStatus.CORRECT) {
			this.setIcon(Icons.CORRECT);
		} else if (exercise.getStatus() != ExerciseStatus.NOT_ATTEMPTED) {
			this.setIcon(Icons.INCORRECT);
		}
	}
}
