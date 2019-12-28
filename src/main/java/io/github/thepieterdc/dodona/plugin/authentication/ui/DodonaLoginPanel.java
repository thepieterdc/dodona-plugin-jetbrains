/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package io.github.thepieterdc.dodona.plugin.authentication.ui;

import io.github.thepieterdc.dodona.plugin.api.DodonaExecutorImpl;
import be.ugent.piedcler.dodona.plugin.notifications.Notifier;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.project.Project;
import com.intellij.ui.HoverHyperlinkLabel;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.exceptions.AuthenticationException;
import io.github.thepieterdc.dodona.resources.User;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;

/**
 * Center component of the login dialog.
 */
public class DodonaLoginPanel extends JPanel {
	private JTextField hostField;
	private JPasswordField tokenField;
	
	private JPanel helpPanel;
	private JButton testBtn;
	
	private JPanel mainPane;
	
	/**
	 * CredentialsPanel constructor.
	 *
	 * @param project        active project
	 * @param showTestButton true to show the test connection button
	 */
	public DodonaLoginPanel(@Nonnull final Project project, final boolean showTestButton) {
		super(new BorderLayout());
		this.add(this.mainPane, BorderLayout.CENTER);
		
		this.hostField.setText(DodonaClient.DEFAULT_HOST);
		
		this.testBtn.addActionListener(e -> this.testCredentials(project));
		this.testBtn.setVisible(showTestButton);
	}
	
	/**
	 * Custom creator for the help text.
	 */
	private void createUIComponents() {
		this.helpPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		this.helpPanel.setBorder(BorderFactory.createEmptyBorder());
		
		this.helpPanel.add(new JLabel("Need help generating a token? "));
		
		final HoverHyperlinkLabel readmeLink = new HoverHyperlinkLabel("View instructions");
		readmeLink.addHyperlinkListener(e -> {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				BrowserUtil.browse("https://github.com/thepieterdc/dodona-plugin-jetbrains/blob/master/README.md");
			}
		});
		
		this.helpPanel.add(readmeLink);
	}
	
	/**
	 * Gets the entered host.
	 *
	 * @return the host
	 */
	@Nonnull
	public String getHost() {
		return String.valueOf(this.hostField.getText()).trim();
	}
	
	/**
	 * Gets the entered token.
	 *
	 * @return the token
	 */
	@Nonnull
	public String getToken() {
		return String.valueOf(this.tokenField.getPassword()).trim();
	}
	
	/**
	 * Sets the value of the token field.
	 *
	 * @param token the value to set
	 */
	public void setToken(@Nonnull final String token) {
		this.tokenField.setText(token);
	}
	
	/**
	 * Tests the provided access token.
	 *
	 * @param project active project
	 */
	private void testCredentials(@Nonnull final Project project) {
		try {
			final User user = DodonaExecutorImpl.callModal(
				project, "Verifying credentials", this.getHost(), this.getToken(), DodonaClient::me
			);
			
			Notifier.success(this.mainPane,
				String.format("Successfully authenticated as %s %s.", user.getFirstName(), user.getLastName())
			);
		} catch (final AuthenticationException ex) {
			Notifier.error(this.mainPane, "The provided token was not valid.", ex);
		} catch (final IOException | RuntimeException ex) {
			Notifier.error(this.mainPane, String.format("Could not authenticate: %s", ex.getMessage()), ex);
		}
	}
}