/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package io.github.thepieterdc.dodona.plugin.ui.resources.series;

import com.intellij.ui.ColoredListCellRenderer;
import io.github.thepieterdc.dodona.resources.Series;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Renders the series name correctly in a list of Series.
 */
public class SeriesNameRenderer extends ColoredListCellRenderer<Series> {
	@Override
	protected void customizeCellRenderer(@NotNull JList<? extends Series> list,
	                                     @Nonnull final Series series,
	                                     int index,
	                                     boolean selected,
	                                     boolean hasFocus) {
		this.append(series.getName());
	}
}
