/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.authentication;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Shows a login dialog to authenticate.
 */
class LoginDialog extends DialogWrapper {
	private final CredentialsPanel credentialsPanel;
	
	/**
	 * LoginDialog constructor.
	 *
	 * @param project the current project
	 */
	LoginDialog(@Nonnull final Project project) {
		super(project, true);
		
		this.credentialsPanel = new CredentialsPanel(project);
		
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
	 * Gets the authentication token provided by the user.
	 *
	 * @return the authentication token
	 */
	@Nonnull
	String getToken() {
		return this.credentialsPanel.getToken();
	}
}