/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.plugin.ui.listeners.SelectedItemListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

/**
 * A dialog that allows the user to select something.
 */
public abstract class SelectionDialog<T> extends JDialog {
	/**
	 * Adds a listener to the current selected item.
	 *
	 * @param listener the listener
	 */
	abstract public void addListener(@Nonnull final SelectedItemListener<T> listener);
	
	/**
	 * Gets the selected item.
	 *
	 * @return the selected item
	 */
	@Nullable
	abstract public T getSelectedItem();
	
	/**
	 * Gets whether or not items can be selected from this dialog.
	 *
	 * @return true if items exist
	 */
	abstract public boolean hasItems();
}
