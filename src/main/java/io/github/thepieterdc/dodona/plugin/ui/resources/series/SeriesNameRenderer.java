/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.series;

import com.intellij.ui.ColoredListCellRenderer;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.resources.Series;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * Renders the course name.
 */
final class SeriesNameRenderer extends ColoredListCellRenderer<Series> {
	@Override
	protected void customizeCellRenderer(final JList<? extends Series> jList,
	                                     @Nullable final Series series,
	                                     final int i,
	                                     final boolean b,
	                                     final boolean b1) {
		if (series == null) {
			this.append(DodonaBundle.message("dialog.select_exercise.series.placeholder"));
		} else {
			this.append(series.getName());
		}
	}
}