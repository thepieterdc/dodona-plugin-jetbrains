/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.selection;

import be.ugent.piedcler.dodona.resources.Series;

import javax.swing.*;
import java.awt.*;

/**
 * Renders the course name correctly in a list of Series.
 */
public class SeriesListRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 5613848038759337370L;
	
	@Override
	public Component getListCellRendererComponent(final JList<?> list,
	                                              final Object value,
	                                              final int index,
	                                              final boolean isSelected,
	                                              final boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, false);
		if (value instanceof Series) {
			final Series series = (Series) value;
			this.setText(series.getName());
		}
		return this;
	}
}
