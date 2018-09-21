/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.settings;

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

	private JLabel lblDodonaURL;
	private JTextField fieldDodonaURL;

	@Override
	public void apply() {
		SettingsHelper.setApiKey(this.fieldApiKey.getText().trim());
		SettingsHelper.setDodonaURL(this.fieldDodonaURL.getText().trim());
	}

	@Nullable
	@Override
	public JComponent createComponent() {

		this.updateApiKeyFromSettings();
		this.updateDodonaURLFromSettings();

		return this.panelRoot;
	}

	@Override
	public void disposeUIResources() {

	}

	@Nls
	@Override
	public String getDisplayName() {
		return "Dodona Settings";
	}

	@Override
	public boolean isModified() {
		return !SettingsHelper.getApiKey().equals(this.fieldApiKey.getText().trim())
			|| ! SettingsHelper.getDodonaURL().equals(this.fieldDodonaURL.getText().trim());
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

	/**
	 * Sets the value of the Dodona URL field.
	 */
	private void updateDodonaURLFromSettings(){
		this.fieldDodonaURL.setText(SettingsHelper.getDodonaURL());
	}

}
