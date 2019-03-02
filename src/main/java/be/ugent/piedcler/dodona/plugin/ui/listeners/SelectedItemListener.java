/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.listeners;

import javax.annotation.Nullable;

/**
 * Listener for changes to selected items.
 */
@FunctionalInterface
public interface SelectedItemListener<T> {
	/**
	 * Called when the selected item is changed.
	 *
	 * @param selectedValue the value of the current selected item
	 */
	void onItemSelected(@Nullable final T selectedValue);
}
