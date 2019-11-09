/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.submissions;

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
public final class UnknownExercisePanel extends JPanel {
	@NonNls
	private static final JComponent ICON_UNKNOWN = new AnimatedIcon(
		"ICON_UNKNOWN", new Icon[0], Icons.QUESTION.color(TextColors.SECONDARY), 0
	);
	
	private final CodeIdentificationService identificationService;
	
	/**
	 * UnknownExercisePanel constructor.
	 *
	 * @param project the current active project
	 */
	public UnknownExercisePanel(final Project project) {
		super(new GridBagLayout());
		this.identificationService = CodeIdentificationService.getInstance();
		
		final JPanel inner = new JPanel(new BorderLayout(10, 10));
		inner.add(new JLabel(DodonaBundle.message(
			"toolwindow.submissions.unknown")),
			BorderLayout.PAGE_END
		);
		inner.add(ICON_UNKNOWN, BorderLayout.CENTER);
		inner.addMouseListener((ClickListener) e ->
			this.identificationService.identifyCurrent(project)
		);
		inner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.add(inner, new GridBagConstraints());
	}
}
