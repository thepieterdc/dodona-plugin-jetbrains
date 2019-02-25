/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.authentication;

import be.ugent.piedcler.dodona.DodonaClient;
import be.ugent.piedcler.dodona.plugin.reporting.NotificationReporter;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * Center component of the login dialog.
 */
public class CredentialsPanel extends JPanel {
	private JTextField hostField;
	private JPasswordField tokenField;
	
	private JTextPane helpTxt;
	private JButton testBtn;
	
	private JPanel mainPane;
	
	/**
	 * CredentialsPanel constructor.
	 */
	public CredentialsPanel() {
		super(new BorderLayout());
		this.add(this.mainPane, BorderLayout.CENTER);
		
		this.hostField.setText(DodonaClient.DEFAULT_HOST);
		
		this.helpTxt.addHyperlinkListener(e -> BrowserUtil.browse(e.getURL()));
		this.helpTxt.setBackground(UIUtil.TRANSPARENT_COLOR);
		this.helpTxt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.helpTxt.setMargin(JBUI.insetsTop(5));
		this.helpTxt.setText("<html>Need help generating a token? <a href=\"https://github.com/thepieterdc/dodona-plugin-jetbrains/blob/master/README.md\">View instructions</a></html>");
	
		this.testBtn.addActionListener(e -> this.testCredentials());
	}
	
	/**
	 * Gets the entered token.
	 *
	 * @return the token
	 */
	@Nonnull
	String getToken() {
		return String.valueOf(this.tokenField.getPassword());
	}
	
	/**
	 * Tests the provided access token.
	 */
	private void testCredentials() {
		Messages.showInfoMessage(this.mainPane, "test", "test");
		NotificationReporter.info("Successfully authenticated as Pieter De Clercq.");
	}
}