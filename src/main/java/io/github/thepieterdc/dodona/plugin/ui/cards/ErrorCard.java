/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.cards;

import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

/**
 * Card to display when an error has occurred.
 */
public final class ErrorCard extends IconTextCard {
	@NonNls
	private static final JComponent ICON = Icons.toComponent(
		Icons.ERROR.color(TextColors.SECONDARY)
	);
	
	/**
	 * ErrorCard constructor.
	 */
	public ErrorCard(@Nls final String message) {
		super(ICON, message);
	}
}
