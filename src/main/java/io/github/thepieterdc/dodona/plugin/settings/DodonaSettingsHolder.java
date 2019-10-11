/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings;

import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccountManager;

import javax.annotation.Nonnull;

/**
 * Provides access to different plugin settings.
 */
public class DodonaSettingsHolder {
	private final DodonaAccountManager accounts;
	
	/**
	 * SettingsHolder constructor.
	 *
	 * @param accounts account manager
	 */
	DodonaSettingsHolder(final DodonaAccountManager accounts) {
		this.accounts = accounts;
	}
	
	/**
	 * Gets the account manager.
	 *
	 * @return the account manager
	 */
	@Nonnull
	public DodonaAccountManager accounts() {
		return this.accounts;
	}
}
