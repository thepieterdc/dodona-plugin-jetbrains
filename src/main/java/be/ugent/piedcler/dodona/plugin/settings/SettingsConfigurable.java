/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Controller for changing plugin settings.
 */
public class SettingsConfigurable implements SearchableConfigurable {
	@Nullable
	private SettingsPanel settingsPane;
	
	/**
	 * SettingsConfigurable constructor.
	 */
	public SettingsConfigurable() {
	
	}
	
	@Override
	public void apply() throws ConfigurationException {
		if(this.settingsPane != null) {
			this.settingsPane.apply();
		}
	}
	
	@Nonnull
	@Override
	public JComponent createComponent() {
		if(this.settingsPane == null) {
			this.settingsPane = new SettingsPanel();
		}
		return this.settingsPane.getPanel();
	}
	
	@Override
	public void disposeUIResources() {
		this.settingsPane = null;
	}
	
	@Nls(capitalization = Nls.Capitalization.Title)
	@Override
	public String getDisplayName() {
		return "Dodona";
	}
	
	@Nonnull
	@Override
	public String getHelpTopic() {
		return "settings.dodona";
	}
	
	@NotNull
	@Override
	public String getId() {
		return this.getHelpTopic();
	}
	
	@Override
	public boolean isModified() {
		return this.settingsPane != null && this.settingsPane.isModified();
	}
	
	@Override
	public void reset() {
		if(this.settingsPane != null) {
			this.settingsPane.reset();
		}
	}
}
