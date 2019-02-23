/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.authentication;

import com.intellij.ide.BrowserUtil;
import com.intellij.ui.HyperlinkAdapter;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

/**
 * Center component of the login dialog.
 */
class CredentialsPanel extends JPanel {
	private JTextField fieldHost;
	private JPasswordField fieldToken;
	
	private JTextPane txtHelp;
	private JPanel paneMain;
	
	/**
	 * CredentialsPanel constructor.
	 *
	 * @param oldData old authentication data
	 */
	CredentialsPanel(@Nullable final AuthenticationData oldData) {
		super(new BorderLayout());
		this.add(this.paneMain, BorderLayout.CENTER);
		
		this.txtHelp.setBackground(UIUtil.TRANSPARENT_COLOR);
		this.txtHelp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.txtHelp.setMargin(JBUI.insetsTop(5));
		this.txtHelp.setText("<html>Need help generating a token? <a href=\"https://github.com/thepieterdc/dodona-plugin-jetbrains/blob/master/README.md\">View instructions</a></html>");
		
		this.txtHelp.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			protected void hyperlinkActivated(HyperlinkEvent e) {
				BrowserUtil.browse(e.getURL());
			}
		});
		
		if (oldData != null) {
			this.fieldHost.setText(oldData.getHost());
			this.fieldToken.setText(oldData.getToken());
		}
	}
	
	/**
	 * Gets the authentication data.
	 *
	 * @return the authentication data
	 */
	@Nonnull
	AuthenticationData getAuthenticationData() {
		final String host = this.fieldHost.getText().trim();
		final String token = String.valueOf(this.fieldToken.getPassword());
		return AuthenticationData.create(host, token);
	}
}
