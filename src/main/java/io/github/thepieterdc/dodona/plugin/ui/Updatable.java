/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui;

/**
 * A component that can be updated.
 */
@FunctionalInterface
public interface Updatable {
	/**
	 * Requests an update of the content.
	 */
	void requestUpdate();
}
