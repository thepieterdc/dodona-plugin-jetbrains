/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package io.github.thepieterdc.dodona.plugin.authentication.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Shows a login dialog to authenticate.
 */
public class DodonaLoginDialog extends DialogWrapper {
	private final DodonaLoginPanel credentialsPanel;
	
	/**
	 * LoginDialog constructor.
	 *
	 * @param project the current project
	 * @param oldToken the old, invalid token
	 */
	public DodonaLoginDialog(@Nonnull final Project project, @Nonnull final String oldToken) {
		super(project, true);
		
		this.credentialsPanel = new DodonaLoginPanel(project, false);
		this.credentialsPanel.setToken(oldToken);
		
		this.setTitle("Login to Dodona");
		this.setOKButtonText("Authenticate");
		this.init();
	}
	
	@Nonnull
	@Override
	protected JComponent createCenterPanel() {
		return this.credentialsPanel;
	}
	
	/**
	 * Gets the token provided by the user.
	 *
	 * @return the token
	 */
	@Nonnull
	public String getToken() {
		return this.credentialsPanel.getToken();
	}
}