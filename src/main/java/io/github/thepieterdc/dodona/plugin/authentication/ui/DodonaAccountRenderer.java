/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.ui;

import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import io.github.thepieterdc.dodona.plugin.api.DodonaServer;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccount;
import io.github.thepieterdc.dodona.plugin.ui.IconListCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Renders a Dodona account in a list.
 */
public final class DodonaAccountRenderer extends IconListCellRenderer<DodonaAccount> {
	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private JLabel email;
	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private JLabel fullName;
	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private JLabel server;
	
	/**
	 * DodonaAccountRenderer constructor.
	 */
	DodonaAccountRenderer() {
		super(JBUI.Borders.empty(5, 8));
	}
	
	@Override
	protected void addDetails(final JPanel container, final GridBag insets) {
		container.add(this.fullName, insets.nextLine().next());
		container.add(this.email, insets.next());
		container.add(this.server, insets.nextLine().coverLine());
	}
	
	@Override
	protected void initialize() {
		this.email = new JLabel();
		this.fullName = new JLabel();
		this.server = new JLabel();
	}
	
	@Override
	protected void renderValue(final DodonaAccount account,
	                           final Color primary,
	                           final Color secondary) {
		// Set the icon.
		this.icon.setIcon(DodonaServer.toIcon(account.getServer()));
		
		// Set the email field.
		this.email.setForeground(secondary);
		this.email.setText(account.getEmail());
		
		// Set the fullname field.
		this.fullName.setForeground(primary);
		this.fullName.setText(account.getFullName());
		this.fullName.setFont(this.fullName.getFont().deriveFont(Font.BOLD));
		
		// Set the server field.
		this.server.setForeground(secondary);
		this.server.setText(DodonaServer.toDisplayName(account.getServer()));
	}
}
