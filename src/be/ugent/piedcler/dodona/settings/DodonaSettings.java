/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.settings;

import be.ugent.piedcler.dodona.resources.DodonaBundle;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Controls the settings of the plugin.
 */
public class DodonaSettings implements Configurable {
	private JPanel panelRoot;
	private JLabel lblFormDescription;
	private JLabel lblApiKey;
	private JLabel lblApiKeyInstructions;
	private JTextField fieldApiKey;
	
	@Override
	public void apply() {
		SettingsHelper.setApiKey(this.fieldApiKey.getText().trim());
	}
	
	@Nullable
	@Override
	public JComponent createComponent() {
		this.lblFormDescription.setText(DodonaBundle.message("settings.description"));
		
		this.lblApiKey.setText(DodonaBundle.message("settings.apikey.title"));
		this.lblApiKeyInstructions.setText(DodonaBundle.message("settings.apikey.instructions"));
		this.fieldApiKey.setToolTipText(DodonaBundle.message("settings.apikey.tooltip"));
		this.updateApiKeyFromSettings();
		
		return this.panelRoot;
	}
	
	@Override
	public void disposeUIResources() {
	
	}
	
	@Nls
	@Override
	public String getDisplayName() {
		return DodonaBundle.message("settings.display.name");
	}
	
	@Override
	public boolean isModified() {
		return !SettingsHelper.getApiKey().equals(this.fieldApiKey.getText().trim());
	}
	
	@Override
	public void reset() {
	
	}
	
	/**
	 * Sets the value of the API key field.
	 */
	private void updateApiKeyFromSettings() {
		this.fieldApiKey.setText(SettingsHelper.getApiKey());
	}
}
