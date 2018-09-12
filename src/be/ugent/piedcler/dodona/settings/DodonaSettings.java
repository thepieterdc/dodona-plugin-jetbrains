/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
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
	private JTextField fieldApiKey;
	
	@Override
	public void apply() {
		System.out.println(this.fieldApiKey.getText().trim());
	}
	
	@Nullable
	@Override
	public JComponent createComponent() {
		this.lblFormDescription.setText(DodonaBundle.message("settings.description"));
		this.lblApiKey.setText(DodonaBundle.message("settings.apikey.title"));
		this.fieldApiKey.setToolTipText(DodonaBundle.message("settings.apikey.tooltip"));
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
		return false;
	}
	
	@Override
	public void reset() {
	
	}
}
