/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.ConfigurableUi;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Pair;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccount;
import io.github.thepieterdc.dodona.plugin.authentication.ui.DodonaAccountPanel;
import io.github.thepieterdc.dodona.plugin.settings.DodonaSettingsHolder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Main panel for plugin settings.
 */
public class DodonaSettingsPanel implements ConfigurableUi<DodonaSettingsHolder>, Disposable {
	private final DodonaAccountPanel accountPanel;
	
	/**
	 * DodonaSettingsPanel constructor.
	 */
	public DodonaSettingsPanel() {
		this.accountPanel = new DodonaAccountPanel();
	}
	
	@Override
	public void apply(@NotNull final DodonaSettingsHolder holder) {
		// Set the account.
		final Pair<DodonaAccount, String> account = this.accountPanel.getAccount();
		if (account.getFirst() == null) {
			// Clear the previous account.
			holder.accounts().clearAccount();
		} else if (!holder.accounts().hasAccount(account.getFirst())) {
			// Set the new account.
			holder.accounts().setAccount(account.getFirst(), account.getSecond());
		}
		
		// Clear the new authentication data and the modification status.
		this.accountPanel.clearModified();
		this.accountPanel.clearNewData();
	}
	
	@Override
	public void dispose() {
		Disposer.dispose(this.accountPanel);
	}
	
	@NotNull
	@Override
	public JComponent getComponent() {
		return this.accountPanel;
	}
	
	@Override
	public boolean isModified(@NotNull final DodonaSettingsHolder s) {
		return this.accountPanel.isModified();
	}
	
	@Override
	public void reset(@NotNull final DodonaSettingsHolder holder) {
		// Set the accounts.
		this.accountPanel.setAccount(
			holder.accounts().getAccount().orElse(null)
		);
		
		// Clear the new authentication data and the modification status.
		this.accountPanel.clearModified();
		this.accountPanel.clearNewData();
	}
}
