/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.ui;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import io.github.thepieterdc.dodona.plugin.api.DodonaServer;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccount;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;

/**
 * Renders a Dodona account in a list.
 */
public final class DodonaAccountRenderer extends JPanel implements ListCellRenderer<DodonaAccount> {
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
	
	private final JLabel avatar;
	
	private final JLabel email;
	private final JLabel fullName;
	
	private final JLabel server;
	
	/**
	 * DodonaAccountRenderer constructor.
	 */
	DodonaAccountRenderer() {
		super();
		this.avatar = new JLabel();
		this.email = new JLabel();
		this.fullName = new JLabel();
		this.server = new JLabel();
		
		this.createLayout();
	}
	
	/**
	 * Creates the layout of the cell.
	 */
	private void createLayout() {
		// Main cell.
		this.setBorder(JBUI.Borders.empty(5, 8));
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		// Account info.
		final JPanel infoPanel = new JPanel();
		infoPanel.setBorder(JBUI.Borders.empty(0, 6, 4, 6));
		infoPanel.setLayout(new GridBagLayout());
		
		final GridBag infoDetails = new GridBag()
			.setDefaultAnchor(GridBagConstraints.LINE_START)
			.setDefaultFill(GridBagConstraints.VERTICAL)
			.setDefaultInsets(JBUI.insetsRight(UIUtil.DEFAULT_HGAP));
		infoPanel.add(this.fullName, infoDetails.nextLine().next());
		infoPanel.add(this.email, infoDetails.next());
		infoPanel.add(this.server, infoDetails.nextLine().coverLine());
		
		// Avatar.
		this.add(this.avatar);
		this.add(infoPanel);
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
				? JBColor.namedColor(DodonaAccountRenderer.selectedFocusedBackground, DodonaAccountRenderer.defaultSelectedFocusedBackground)
				: JBColor.namedColor(DodonaAccountRenderer.selectedBackground, DodonaAccountRenderer.defaultSelectedBackground);
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
				? JBColor.namedColor(DodonaAccountRenderer.selectedFocusedForeground, UIUtil.getListForeground())
				: JBColor.namedColor(DodonaAccountRenderer.selectedForeground, UIUtil.getListForeground());
		}
		return JBColor.namedColor(DodonaAccountRenderer.unselectedForeground, UIUtil.getListForeground());
	}
	
	@Override
	public Component getListCellRendererComponent(final JList<? extends DodonaAccount> list,
	                                              final DodonaAccount account,
	                                              final int index,
	                                              final boolean isSelected,
	                                              final boolean cellHasFocus) {
		// Set the cell background color.
		UIUtil.setBackgroundRecursively(this, DodonaAccountRenderer.getBackground(list, isSelected));
		
		// Set the text colors.
		final Color primary = DodonaAccountRenderer.getForeground(list, isSelected);
		final Color secondary = DodonaAccountRenderer.getSecondaryForeground(list, isSelected);
		
		// Set the fields.
		this.avatar.setIcon(DodonaServer.toIcon(account.getServer()));
		
		this.email.setForeground(secondary);
		this.email.setText(account.getEmail());
		
		this.fullName.setForeground(primary);
		this.fullName.setText(account.getFullName());
		this.fullName.setFont(this.fullName.getFont().deriveFont(Font.BOLD));
		
		this.server.setForeground(secondary);
		this.server.setText(DodonaServer.toDisplayName(account.getServer()));
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
			return DodonaAccountRenderer.getForeground(list, true);
		}
		return JBColor.namedColor(DodonaAccountRenderer.unselectedSecondaryForeground, UIUtil.getContextHelpForeground());
	}
}
