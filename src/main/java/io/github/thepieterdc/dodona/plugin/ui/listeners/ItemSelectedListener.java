/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.listeners;

import javax.annotation.Nullable;

/**
 * Listener for selection of an item in a list.
 */
@FunctionalInterface
public interface ItemSelectedListener<T> {
	/**
	 * Called when a new item is changed.
	 *
	 * @param selectedValue the current selected item, can be null if nothing is
	 *                      selected
	 */
	void onItemSelected(@Nullable final T selectedValue);
}
