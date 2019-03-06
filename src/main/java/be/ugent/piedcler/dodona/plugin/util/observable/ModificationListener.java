/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.util.observable;

import javax.annotation.Nullable;

/**
 * A listener that is called upon modifications to an observable.
 */
@FunctionalInterface
public interface ModificationListener<T> {
	/**
	 * Called when an observable is modified.
	 *
	 * @param newValue the new value of the observable
	 */
	void onModified(@Nullable T newValue);
}
