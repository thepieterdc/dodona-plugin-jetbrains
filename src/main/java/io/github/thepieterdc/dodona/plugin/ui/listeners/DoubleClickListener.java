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

/**
 * Listener for double click events.
 */
@FunctionalInterface
public interface DoubleClickListener extends ClickListener {
	@Override
	default void mouseClicked(final MouseEvent e) {
		if (e.getClickCount() == 2) {
			this.onDoubleClick(e);
		}
	}
	
	/**
	 * Called when a double click event is detected.
	 */
	void onDoubleClick(MouseEvent e);
}
