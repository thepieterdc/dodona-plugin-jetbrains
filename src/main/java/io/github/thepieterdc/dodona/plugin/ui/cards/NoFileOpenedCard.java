/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.cards;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;

import javax.swing.*;

/**
 * Card to display when no file is opened.
 */
public final class NoFileOpenedCard extends IconTextCard {
	private static final JComponent ICON = Icons.toComponent(
		Icons.FILE_CODE.color(TextColors.SECONDARY)
	);
	
	/**
	 * NoFileOpenedCard constructor.
	 */
	public NoFileOpenedCard() {
		super(ICON, DodonaBundle.message("card.no_file_opened.message"));
	}
}
