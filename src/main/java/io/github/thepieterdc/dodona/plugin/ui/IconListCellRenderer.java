/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui;

import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * List renderer that contains an icon on the left and details on the right.
 *
 * @param <T> type of the list cell object
 */
public abstract class IconListCellRenderer<T> extends AbstractListCellRenderer<T> {
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
	
	@Override
	public Component getListCellRendererComponent(final JList<? extends T> list,
	                                              final T value,
	                                              final int index,
	                                              final boolean isSelected,
	                                              final boolean cellHasFocus) {
		// Set the cell background color.
		UIUtil.setBackgroundRecursively(this, AbstractListCellRenderer.getBackground(list, isSelected));
		
		// Determine the text colors.
		final Color primary = AbstractListCellRenderer.getForeground(list, isSelected);
		final Color secondary = AbstractListCellRenderer.getSecondaryForeground(list, isSelected);
		
		// Set the field values.
		this.renderValue(value, primary, secondary);
		
		return this;
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
