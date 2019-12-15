/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;

import javax.swing.*;

/**
 * Panel to display when no file is opened.
 */
public final class NoFileOpenedPanel extends IconTextPanel {
	private static final JComponent ICON = Icons.toComponent(
		Icons.FILE_CODE.color(TextColors.SECONDARY)
	);
	
	/**
	 * NoFileOpenedPanel constructor.
	 */
	public NoFileOpenedPanel() {
		super(ICON, DodonaBundle.message("panel.no_file_opened.message"));
	}
}
