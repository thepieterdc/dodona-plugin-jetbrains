/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.util;

import java.awt.*;

/**
 * Utilities related to fonts.
 */
public final class FontUtils {
	/**
	 * FontUtil constructor.
	 */
	private FontUtils() {
		// Utility class.
	}
	
	/**
	 * Boldens the font used in the given component if the given condition
	 * holds.
	 *
	 * @param component the component to change the font for
	 * @param condition the condition to bolden the font
	 */
	public static void boldenIf(final Component component, final boolean condition) {
		component.setFont(boldenIf(component.getFont(), condition));
	}
	
	/**
	 * Boldens the font if the given condition holds.
	 *
	 * @param font      the font to adapt
	 * @param condition the condition to bolden the font
	 * @return the font
	 */
	public static Font boldenIf(final Font font, final boolean condition) {
		if (condition) {
			return font.deriveFont(Font.BOLD);
		}
		return font.deriveFont(Font.PLAIN);
	}
}
