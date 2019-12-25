/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Listener for click events.
 */
@FunctionalInterface
public interface ClickListener extends MouseListener {
	@Override
	default void mouseEntered(final MouseEvent e) {
		// Not implemented.
	}
	
	@Override
	default void mouseExited(final MouseEvent e) {
		// Not implemented.
	}
	
	@Override
	default void mousePressed(final MouseEvent e) {
		// Not implemented.
	}
	
	@Override
	default void mouseReleased(final MouseEvent e) {
		// Not implemented.
	}
}
