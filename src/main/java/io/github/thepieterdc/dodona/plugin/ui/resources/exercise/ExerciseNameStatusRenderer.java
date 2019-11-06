/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.exercise;

import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.util.ui.JBUI;
import io.github.thepieterdc.dodona.data.ExerciseStatus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.Icons;
import io.github.thepieterdc.dodona.resources.Exercise;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.EnumMap;
import java.util.Map;

/**
 * Renders the exercise name and completion status.
 */
final class ExerciseNameStatusRenderer extends ColoredListCellRenderer<Exercise> {
	private final Map<ExerciseStatus, Icon> icons;
	
	/**
	 * ExerciseNameStatusRenderer constructor.
	 */
	public ExerciseNameStatusRenderer() {
		this.icons = new EnumMap<>(ExerciseStatus.class);
		this.icons.put(ExerciseStatus.CORRECT, Icons.EXERCISE_CORRECT);
		this.icons.put(ExerciseStatus.HAS_BEEN_CORRECT, Icons.EXERCISE_CORRECT);
		this.icons.put(ExerciseStatus.INCORRECT, Icons.EXERCISE_WRONG.color(
			JBUI.CurrentTheme.Focus.errorColor(true))
		);
	}
	
	@Override
	protected void customizeCellRenderer(@NotNull final JList<? extends Exercise> jList,
	                                     final Exercise exercise,
	                                     final int i,
	                                     final boolean b,
	                                     final boolean b1) {
		if (exercise == null) {
			this.append(DodonaBundle.message("dialog.select_exercise.exercise.placeholder"));
		} else {
			this.append(exercise.getName());
			this.setIcon(this.icons.get(exercise.getStatus()));
		}
	}
}