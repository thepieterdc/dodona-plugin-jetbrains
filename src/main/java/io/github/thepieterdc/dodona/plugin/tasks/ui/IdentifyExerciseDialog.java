/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package io.github.thepieterdc.dodona.plugin.tasks.ui;

import be.ugent.piedcler.dodona.plugin.util.observable.ObservableValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

/**
 * A dialog that allows the user to select something.
 */
public abstract class IdentifyExerciseDialog<T> extends JDialog {
	private final ObservableValue<T> value;
	
	/**
	 * SelectionDialog constructor.
	 *
	 * @param initial initial selected value
	 */
	IdentifyExerciseDialog(@Nullable final T initial) {
		this.value = new ObservableValue<>(initial);
	}
	
	/**
	 * Adds a listener to the current selected item.
	 *
	 * @param listener the listener
	 */
	public void addListener(@Nonnull final be.ugent.piedcler.dodona.plugin.ui.listeners.ItemSelectedListener<T> listener) {
		this.value.addListener(listener::onItemSelected);
	}
	
	/**
	 * Gets the selected item.
	 *
	 * @return the selected item
	 */
	@Nullable
	public T getSelectedItem() {
		return this.value.getValue();
	}
	
	/**
	 * Gets whether or not items can be selected from this dialog.
	 *
	 * @return true if items exist
	 */
	abstract public boolean hasItems();
	
	/**
	 * Sets the value.
	 *
	 * @param value the value
	 */
	void setValue(@Nullable final T value) {
		this.value.setValue(value);
	}
}
