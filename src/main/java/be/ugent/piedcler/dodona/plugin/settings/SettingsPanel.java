/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.settings;

import be.ugent.piedcler.dodona.plugin.ui.auth.CredentialsPanel;
import com.intellij.openapi.project.ProjectManager;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Settings panel to modify plugin settings.
 */
public class SettingsPanel {
	private CredentialsPanel credentialsPanel;
	
	private JPanel mainPane;
	
	/**
	 * Initializes components.
	 */
	private void createUIComponents() {
		this.credentialsPanel = new CredentialsPanel(ProjectManager.getInstance().getDefaultProject(), true);
	}
	
	/**
	 * Gets the main panel.
	 *
	 * @return the main panel
	 */
	public JComponent getPanel() {
		return this.mainPane;
	}
	
	/**
	 * Gets the value of the token field.
	 *
	 * @return the token
	 */
	@Nonnull
	public String getToken() {
		return this.credentialsPanel.getToken();
	}
	
	/**
	 * Sets the value of the token field.
	 *
	 * @param token the token to set
	 */
	public void setToken(@Nonnull final String token) {
		this.credentialsPanel.setToken(token);
	}
}
