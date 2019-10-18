/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
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
	@NonNls
	private static final String unselectedSecondaryForeground = "Component.infoForeground";
	
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
				? JBColor.namedColor(AbstractListCellRenderer.selectedFocusedBackground, AbstractListCellRenderer.defaultSelectedFocusedBackground)
				: JBColor.namedColor(AbstractListCellRenderer.selectedBackground, AbstractListCellRenderer.defaultSelectedBackground);
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
				? JBColor.namedColor(AbstractListCellRenderer.selectedFocusedForeground, UIUtil.getListForeground())
				: JBColor.namedColor(AbstractListCellRenderer.selectedForeground, UIUtil.getListForeground());
		}
		return JBColor.namedColor(AbstractListCellRenderer.unselectedForeground, UIUtil.getListForeground());
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
				? JBColor.namedColor(AbstractListCellRenderer.selectedFocusedForeground, UIUtil.getListForeground())
				: JBColor.namedColor(AbstractListCellRenderer.selectedForeground, UIUtil.getListForeground());
		}
		return JBColor.namedColor(AbstractListCellRenderer.unselectedForeground, UIUtil.getListForeground());
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
			return AbstractListCellRenderer.getForeground(true, focused);
		}
		return JBColor.namedColor(AbstractListCellRenderer.unselectedSecondaryForeground, UIUtil.getContextHelpForeground());
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
			return AbstractListCellRenderer.getForeground(list, true);
		}
		return JBColor.namedColor(AbstractListCellRenderer.unselectedSecondaryForeground, UIUtil.getContextHelpForeground());
	}
}
