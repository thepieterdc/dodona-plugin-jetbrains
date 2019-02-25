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
import be.ugent.piedcler.dodona.plugin.notifications.Notifier;
import com.intellij.ide.BrowserUtil;
import com.intellij.ui.HoverHyperlinkLabel;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

/**
 * Center component of the login dialog.
 */
public class CredentialsPanel extends JPanel {
	private JTextField hostField;
	private JPasswordField tokenField;
	
	private JPanel helpPanel;
	private JButton testBtn;
	
	private JPanel mainPane;
	
	/**
	 * CredentialsPanel constructor.
	 */
	public CredentialsPanel() {
		super(new BorderLayout());
		this.add(this.mainPane, BorderLayout.CENTER);
		
		this.hostField.setText(DodonaClient.DEFAULT_HOST);

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
		Notifier.success(this.mainPane, String.format("Successfully authenticated as %s %s.", "Pieter", "De Clercq"));
	}
	
	private void createUIComponents() {
		this.helpPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		this.helpPanel.setBorder(BorderFactory.createEmptyBorder());
		
		this.helpPanel.add(new JLabel("Need help generating a token? "));
		
		final HoverHyperlinkLabel readmeLink = new HoverHyperlinkLabel("View instructions");
		readmeLink.addHyperlinkListener(e -> {
			if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				BrowserUtil.browse("https://github.com/thepieterdc/dodona-plugin-jetbrains/blob/master/README.md");
			}
		});
		
		this.helpPanel.add(readmeLink);
	}
}