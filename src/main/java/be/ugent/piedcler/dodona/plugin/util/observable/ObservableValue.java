/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.util.observable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * A resource that can be observed for changes.
 */
public class ObservableValue<T> {
	private final Collection<ModificationListener<T>> listeners = new HashSet<>();
	
	@Nullable
	private T value;
	
	/**
	 * ObservableValue constructor.
	 *
	 * @param initial the initial value
	 */
	public ObservableValue(@Nullable T initial) {
		this.value = initial;
	}
	
	/**
	 * Adds a listener to the list of listeners.
	 *
	 * @param listener the listener to add
	 */
	public void addListener(@Nonnull final ModificationListener<T> listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Gets the value of the observable.
	 *
	 * @return the value
	 */
	@Nullable
	public T getValue() {
		return this.value;
	}
	
	/**
	 * Sets the value of the observable. If the new value is equal to the
	 * previous value, the listeners will not be called.
	 *
	 * @param value the new value to set
	 */
	public void setValue(@Nullable final T value) {
		if (!Objects.equals(this.value, value)) {
			this.value = value;
			this.listeners.forEach(listener -> listener.onModified(value));
		}
	}
}
