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
import javax.annotation.Nullable;
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
	 * @param oldData the old, invalid authentication data
	 */
	LoginDialog(@Nonnull final Project project, @Nullable final AuthenticationData oldData) {
		super(project, true);
		
		this.credentialsPanel = new CredentialsPanel(oldData);
		
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
	 * Gets the authentication data provided by the user.
	 *
	 * @return the authentication data
	 */
	@Nonnull
	AuthenticationData getAuthenticationData() {
		return this.credentialsPanel.getAuthenticationData();
	}
}
