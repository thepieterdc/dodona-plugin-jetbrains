/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui.panels.async;

/**
 * Content card of an AsyncPanel.
 *
 * @param <T> type class of the content
 */
@FunctionalInterface
public interface AsyncPanelContent<T> {
	/**
	 * Updates the data in the panel.
	 *
	 * @param data the new data
	 */
	void update(T data);
}
