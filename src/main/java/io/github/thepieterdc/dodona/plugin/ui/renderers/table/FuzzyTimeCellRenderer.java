/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.renderers.table;

import io.github.thepieterdc.dodona.plugin.ui.util.TimeUtils;

import javax.swing.table.DefaultTableCellRenderer;
import java.time.ZonedDateTime;

/**
 * Table renderer that renders a fuzzy timestamp.
 */
public class FuzzyTimeCellRenderer extends DefaultTableCellRenderer {
	@Override
	protected void setValue(final Object value) {
		if (value instanceof ZonedDateTime) {
			final ZonedDateTime dateTime = (ZonedDateTime) value;
			super.setValue(TimeUtils.fuzzy(dateTime));
		} else {
			super.setValue(value);
		}
	}
}
