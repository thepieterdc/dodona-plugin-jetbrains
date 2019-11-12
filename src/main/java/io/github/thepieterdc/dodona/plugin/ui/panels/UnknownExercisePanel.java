/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels;

import com.intellij.openapi.project.Project;
import com.intellij.util.ui.AnimatedIcon;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentificationService;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;
import io.github.thepieterdc.dodona.plugin.ui.listeners.ClickListener;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;

/**
 * Panel to display when the exercise has not yet been identified.
 */
public final class UnknownExercisePanel extends IconTextPanel {
	@NonNls
	private static final JComponent ICON = new AnimatedIcon(
		"ICON_UNKNOWN", new Icon[0], Icons.QUESTION.color(TextColors.SECONDARY), 0
	);
	
	/**
	 * UnknownExercisePanel constructor.
	 *
	 * @param project the current active project
	 */
	public UnknownExercisePanel(final Project project) {
		super(ICON, DodonaBundle.message("panel.unknown_exercise.message"), inner -> {
			inner.addMouseListener((ClickListener) e ->
				CodeIdentificationService.getInstance().identifyCurrent(project)
			);
			inner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		});
	}
}
