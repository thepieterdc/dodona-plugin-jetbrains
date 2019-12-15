/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels;

import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

/**
 * Panel to display when an error has occurred.
 */
public final class ErrorPanel extends IconTextPanel {
	@NonNls
	private static final JComponent ICON = Icons.toComponent(
		Icons.ERROR.color(TextColors.SECONDARY)
	);
	
	/**
	 * ErrorPanel constructor.
	 */
	public ErrorPanel(@Nls final String message) {
		super(ICON, message);
	}
}
