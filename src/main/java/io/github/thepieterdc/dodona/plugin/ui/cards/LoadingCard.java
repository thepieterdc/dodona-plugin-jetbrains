/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.cards;

import com.intellij.util.ui.AsyncProcessIcon;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

/**
 * Card to display when the content is being loaded.
 */
public final class LoadingCard extends IconTextCard {
	/**
	 * LoadingCard constructor.
	 *
	 * @param text the text ot display underneath the spinner
	 * @param icon the loading icon
	 */
	private LoadingCard(@Nls final String text, final JComponent icon) {
		super(icon, text);
	}
	
	/**
	 * Creates a new loading card.
	 *
	 * @param text   the text to display underneath the spinner
	 * @param parent the parent panel
	 * @return the loading card
	 */
	public static LoadingCard create(@Nls final String text,
	                                 final JPanel parent) {
		final AsyncProcessIcon icon = new AsyncProcessIcon.Big(parent + ".loading");
		icon.setBackground(parent.getBackground());
		return new LoadingCard(text, icon);
	}
}
