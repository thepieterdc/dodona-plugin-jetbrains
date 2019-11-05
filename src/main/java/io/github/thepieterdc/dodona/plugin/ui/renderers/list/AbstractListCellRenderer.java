/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.renderers.list;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

/**
 * Common list cell renderer.
 *
 * @param <T> type of the list cell object
 */
public abstract class AbstractListCellRenderer<T> extends JPanel implements ListCellRenderer<T> {
	private static final Color defaultSelectedFocusedBackground = new JBColor(0xE9EEF5, 0x464A4D);
	private static final Color defaultSelectedBackground = new JBColor(0xF5F5F5, 0x464A4D);
	
	@NonNls
	private static final String selectedFocusedBackground = "Table.lightSelectionBackground";
	@NonNls
	private static final String selectedBackground = "Table.lightSelectionInactiveBackground";
	@NonNls
	private static final String selectedFocusedForeground = "Table.lightSelectionForeground";
	@NonNls
	private static final String selectedForeground = "Table.lightSelectionInactiveForeground";
	@NonNls
	private static final String unselectedForeground = "Table.foreground";
	
	/**
	 * AbstractListCellRenderer constructor.
	 */
	protected AbstractListCellRenderer() {
		super();
	}
	
	/**
	 * Gets the background color.
	 *
	 * @param selected true if the item is the selected item
	 * @return the background color
	 */
	protected static Color getBackground(final JList<?> list, final boolean selected) {
		if (selected) {
			return list.hasFocus()
				? JBColor.namedColor(selectedFocusedBackground, defaultSelectedFocusedBackground)
				: JBColor.namedColor(selectedBackground, defaultSelectedBackground);
		}
		return list.getBackground();
	}
	
	/**
	 * Gets the foreground color.
	 *
	 * @param selected true if the item is the selected item
	 * @param focused  true if the list is focused
	 * @return the foreground color
	 */
	protected static Color getForeground(final boolean selected,
	                                     final boolean focused) {
		if (selected) {
			return focused
				? JBColor.namedColor(selectedFocusedForeground, UIUtil.getListForeground())
				: JBColor.namedColor(selectedForeground, UIUtil.getListForeground());
		}
		return JBColor.namedColor(unselectedForeground, UIUtil.getListForeground());
	}
	
	/**
	 * Gets the foreground color.
	 *
	 * @param selected true if the item is the selected item
	 * @return the foreground color
	 */
	protected static Color getForeground(@Nullable final JList<?> list,
	                                     final boolean selected) {
		if (selected && list != null) {
			return list.hasFocus()
				? JBColor.namedColor(selectedFocusedForeground, UIUtil.getListForeground())
				: JBColor.namedColor(selectedForeground, UIUtil.getListForeground());
		}
		return TextColors.PRIMARY;
	}
	
	/**
	 * Gets the secondary foreground color.
	 *
	 * @param selected true if the item is the selected item
	 * @param focused  true if the list if focused
	 * @return the foreground color
	 */
	protected static Color getSecondaryForeground(final boolean selected,
	                                              final boolean focused) {
		if (selected) {
			return getForeground(true, focused);
		}
		return TextColors.SECONDARY;
	}
	
	/**
	 * Gets the secondary foreground color.
	 *
	 * @param selected true if the item is the selected item
	 * @return the foreground color
	 */
	protected static Color getSecondaryForeground(@Nullable final JList<?> list,
	                                              final boolean selected) {
		if (selected && list != null) {
			return getForeground(list, true);
		}
		return TextColors.SECONDARY;
	}
}
