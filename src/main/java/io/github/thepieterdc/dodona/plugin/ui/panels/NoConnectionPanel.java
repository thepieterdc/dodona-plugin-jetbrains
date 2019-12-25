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
import io.github.thepieterdc.dodona.plugin.ui.Updatable;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Panel to display when the user is not connected to the internet.
 */
public final class NoConnectionPanel extends IconTextPanel {
	private static final JComponent ICON = Icons.toComponent(
		Icons.NO_CONNECTION.color(TextColors.SECONDARY)
	);
	
	/**
	 * NoConnectionPanel constructor.
	 *
	 * @param parent the parent panel
	 */
	public NoConnectionPanel(final Updatable parent) {
		super(ICON, DodonaBundle.message("panel.no_connection.message"), parent::requestUpdate);
	}
	
	/**
	 * Creates a new instance of the NoConnectionPanel.
	 *
	 * @param parent the parent panel
	 * @return the instance
	 */
	@Nonnull
	public static NoConnectionPanel create(final Updatable parent) {
		return new NoConnectionPanel(parent);
	}
}
