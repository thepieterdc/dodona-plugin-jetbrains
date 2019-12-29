/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.cards;

import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentificationService;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;

import javax.swing.*;

/**
 * Card to display when the exercise could not be identified.
 */
public final class UnknownExerciseCard extends IconTextCard {
	private static final JComponent ICON = Icons.toComponent(
		Icons.QUESTION.color(TextColors.SECONDARY)
	);
	
	/**
	 * UnknownExerciseCard constructor.
	 *
	 * @param project the current active project
	 */
	public UnknownExerciseCard(final Project project) {
		super(ICON, DodonaBundle.message("card.unknown_exercise.message"), () ->
			CodeIdentificationService.getInstance(project).identifyCurrent()
		);
	}
}
