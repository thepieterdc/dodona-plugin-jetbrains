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
import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * List renderer that contains an icon on the left and details on the right.
 *
 * @param <T> type of the list cell object
 */
public abstract class IconListCellRenderer<T> extends JPanel implements ListCellRenderer<T> {
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
	
	protected final JLabel icon;
	
	/**
	 * IconListCellRenderer constructor.
	 *
	 * @param border the border of the cell
	 */
	protected IconListCellRenderer(final Border border) {
		super();
		this.icon = new JLabel();
		this.initialize();
		this.createLayout(border);
	}
	
	/**
	 * Sets the details fields.
	 *
	 * @param container the fields to add the details
	 * @param insets    line insets
	 */
	protected abstract void addDetails(final JPanel container, final GridBag insets);
	
	/**
	 * Creates the layout of the cell.
	 */
	private void createLayout(final Border border) {
		// Main panel.
		this.setBorder(border);
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		// Details panel.
		final JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(JBUI.Borders.empty(0, 6, 4, 6));
		detailsPanel.setLayout(new GridBagLayout());
		
		final GridBag detailsInsets = new GridBag()
			.setDefaultAnchor(GridBagConstraints.LINE_START)
			.setDefaultFill(GridBagConstraints.VERTICAL)
			.setDefaultInsets(JBUI.insetsRight(UIUtil.DEFAULT_HGAP));
		this.addDetails(detailsPanel, detailsInsets);
		
		// Append the icon and the details panel to the cell.
		this.add(this.icon);
		this.add(detailsPanel);
	}
	
	/**
	 * Gets the background color.
	 *
	 * @param selected true if the item is the selected item
	 * @return the background color
	 */
	private static Color getBackground(final JList<?> list, final boolean selected) {
		if (selected) {
			return list.hasFocus()
				? JBColor.namedColor(IconListCellRenderer.selectedFocusedBackground, IconListCellRenderer.defaultSelectedFocusedBackground)
				: JBColor.namedColor(IconListCellRenderer.selectedBackground, IconListCellRenderer.defaultSelectedBackground);
		}
		return list.getBackground();
	}
	
	/**
	 * Gets the foreground color.
	 *
	 * @param selected true if the item is the selected item
	 * @return the foreground color
	 */
	private static Color getForeground(final JList<?> list, final boolean selected) {
		if (selected) {
			return list.hasFocus()
				? JBColor.namedColor(IconListCellRenderer.selectedFocusedForeground, UIUtil.getListForeground())
				: JBColor.namedColor(IconListCellRenderer.selectedForeground, UIUtil.getListForeground());
		}
		return JBColor.namedColor(IconListCellRenderer.unselectedForeground, UIUtil.getListForeground());
	}
	
	@Override
	public Component getListCellRendererComponent(final JList<? extends T> list,
	                                              final T value,
	                                              final int index,
	                                              final boolean isSelected,
	                                              final boolean cellHasFocus) {
		// Set the cell background color.
		UIUtil.setBackgroundRecursively(this, IconListCellRenderer.getBackground(list, isSelected));
		
		// Determine the text colors.
		final Color primary = IconListCellRenderer.getForeground(list, isSelected);
		final Color secondary = IconListCellRenderer.getSecondaryForeground(list, isSelected);
		
		// Set the field values.
		this.renderValue(value, primary, secondary);
		
		return this;
	}
	
	/**
	 * Gets the secondary foreground color.
	 *
	 * @param selected true if the item is the selected item
	 * @return the foreground color
	 */
	private static Color getSecondaryForeground(final JList<?> list, final boolean selected) {
		if (selected) {
			return IconListCellRenderer.getForeground(list, true);
		}
		return JBColor.namedColor(IconListCellRenderer.unselectedSecondaryForeground, UIUtil.getContextHelpForeground());
	}
	
	/**
	 * Called by the constructor of this class to allow initialization of fields
	 * by the subclass.
	 */
	protected abstract void initialize();
	
	/**
	 * Render one value.
	 *
	 * @param value     the value
	 * @param primary   the primary color
	 * @param secondary the secondary color
	 */
	protected abstract void renderValue(final T value,
	                                    final Color primary,
	                                    final Color secondary);
}
