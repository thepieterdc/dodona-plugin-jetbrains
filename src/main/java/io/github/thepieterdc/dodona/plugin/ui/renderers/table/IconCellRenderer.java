/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.renderers.table;

import com.intellij.util.ui.table.IconTableCellRenderer;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Table renderer that renders an icon.
 */
public class IconCellRenderer extends IconTableCellRenderer<Icon> {
	@Nonnull
	@Override
	protected Icon getIcon(final Icon t, final JTable jTable, final int i) {
		return t;
	}
	
	@Override
	protected void setValue(final Object value) {
		this.setText(null);
	}
}
