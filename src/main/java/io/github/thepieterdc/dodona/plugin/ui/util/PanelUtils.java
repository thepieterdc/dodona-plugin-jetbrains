/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.util;

import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;

/**
 * Utilities to create panels.
 */
public final class PanelUtils {
	/**
	 * PanelUtils constructor.
	 */
	private PanelUtils() {
		// Utility class.
	}
	
	/**
	 * Shows the card with the given name in the parent panel.
	 *
	 * @param parent the container to show the card in
	 * @param card   the card to show
	 */
	public static void showCard(final JPanel parent,
	                            @NonNls final String card) {
		((CardLayout) parent.getLayout()).show(parent, card);
	}
}
