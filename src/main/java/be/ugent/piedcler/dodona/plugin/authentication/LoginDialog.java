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
public class LoginDialog extends DialogWrapper {
	private final CredentialsPanel credentialsPanel;
	
	/**
	 * LoginDialog constructor.
	 *
	 * @param project the current project
	 */
	public LoginDialog(final Project project) {
		super(project, false);
		
		this.credentialsPanel = new CredentialsPanel();
		
		this.setTitle("Login to Dodona");
		this.setOKButtonText("Authenticate");
		this.init();
	}
	
	@Nonnull
	@Override
	protected JComponent createCenterPanel() {
		return this.credentialsPanel;
	}
}
