/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.settings;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.Comparing;
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
	private SettingsPanel settingsPanel;
	
	@Nonnull
	private final DodonaSettings settings;
	
	/**
	 * SettingsConfigurable constructor.
	 */
	public SettingsConfigurable() {
		this.settings = DodonaSettings.getInstance();
	}
	
	@Override
	public void apply() {
		if(this.settingsPanel == null) {
			return;
		}
		
		this.settings.setToken(this.settingsPanel.getToken());
	}
	
	@Nonnull
	@Override
	public JComponent createComponent() {
		if(this.settingsPanel == null) {
			this.settingsPanel = new SettingsPanel();
		}
		return this.settingsPanel.getPanel();
	}
	
	@Override
	public void disposeUIResources() {
		this.settingsPanel = null;
	}
	
	@Nullable
	@Override
	public Runnable enableSearch(String option) {
		return null;
	}
	
	@Nls(capitalization = Nls.Capitalization.Title)
	@Override
	public String getDisplayName() {
		return "Dodona";
	}
	
	@Nullable
	@Override
	public String getHelpTopic() {
		return null;
	}
	
	@NotNull
	@Override
	public String getId() {
		return this.getDisplayName();
	}
	
	@Override
	public boolean isModified() {
		return this.settingsPanel == null
			|| !Comparing.equal(this.settings.getToken(), this.settingsPanel.getToken());
	}
	
	@Override
	public void reset() {
		if(this.settingsPanel == null) {
			return;
		}
		
		this.settingsPanel.setToken(this.settings.getToken());
	}
}
