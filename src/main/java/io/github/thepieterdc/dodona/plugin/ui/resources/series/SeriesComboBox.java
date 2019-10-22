/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.series;

import io.github.thepieterdc.dodona.plugin.ui.listeners.ItemSelectedListener;
import io.github.thepieterdc.dodona.plugin.ui.resources.AbstractResourceComboBox;
import io.github.thepieterdc.dodona.resources.Series;

/**
 * A combo box containing series.
 */
public class SeriesComboBox extends AbstractResourceComboBox<Series> {
	/**
	 * SeriesComboBox constructor.
	 *
	 * @param listener the listener
	 */
	public SeriesComboBox(final ItemSelectedListener<? super Series> listener) {
		super(new SeriesNameRenderer(), listener);
	}
}
