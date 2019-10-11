/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableBase;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccountManager;
import io.github.thepieterdc.dodona.plugin.settings.ui.DodonaSettingsPanel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Controller that handles changing plugin settings.
 */
public class DodonaSettingsConfigurable extends ConfigurableBase<DodonaSettingsPanel, DodonaSettingsHolder> implements Configurable.NoMargin {
	@NonNls
	private static final String HELP_TOPIC = DodonaSettingsConfigurable.SETTINGS_ID;
	
	@NonNls
	private static final String SETTINGS_ID = "settings.dodona";
	
	private final DodonaAccountManager accountManager;
	
	/**
	 * SettingsConfigurable constructor.
	 *
	 * @param accountManager account manager
	 */
	DodonaSettingsConfigurable(final DodonaAccountManager accountManager) {
		super(DodonaSettingsConfigurable.SETTINGS_ID, DodonaBundle.NAME, DodonaSettingsConfigurable.HELP_TOPIC);
		this.accountManager = accountManager;
	}
	
	@Override
	protected DodonaSettingsPanel createUi() {
		return new DodonaSettingsPanel();
	}
	
	@NotNull
	@Override
	protected DodonaSettingsHolder getSettings() {
		return new DodonaSettingsHolder(this.accountManager);
	}
}
