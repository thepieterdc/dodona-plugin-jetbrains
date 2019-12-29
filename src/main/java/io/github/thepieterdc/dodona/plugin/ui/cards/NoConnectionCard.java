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
import io.github.thepieterdc.dodona.plugin.ui.Updatable;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Card to display when the user is not connected to the internet.
 */
public final class NoConnectionCard extends IconTextCard {
	private static final JComponent ICON = Icons.toComponent(
		Icons.NO_CONNECTION.color(TextColors.SECONDARY)
	);
	
	/**
	 * NoConnectionCard constructor.
	 */
	private NoConnectionCard() {
		super(ICON, DodonaBundle.message("card.no_connection.message"));
	}
	
	/**
	 * NoConnectionCard constructor.
	 *
	 * @param parent the parent panel
	 */
	private NoConnectionCard(final Updatable parent) {
		super(ICON, DodonaBundle.message("card.no_connection.message"), parent::requestUpdate);
	}
	
	/**
	 * Creates a new instance of the NoConnectionCard.
	 *
	 * @return the instance
	 */
	@Nonnull
	public static NoConnectionCard create() {
		return new NoConnectionCard();
	}
	
	/**
	 * Creates a new instance of the NoConnectionCard.
	 *
	 * @param parent the parent panel
	 * @return the instance
	 */
	@Nonnull
	public static NoConnectionCard create(final Updatable parent) {
		return new NoConnectionCard(parent);
	}
}
