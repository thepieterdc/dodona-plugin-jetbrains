/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.accounts;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaServer;
import io.github.thepieterdc.dodona.resources.User;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Manages Dodona accounts.
 */
@State(name = DodonaAccountManager.STATE_NAME, storages = @Storage(DodonaAccountManager.STATE_STORAGE))
public class DodonaAccountManager implements PersistentStateComponent<DodonaAccount> {
	@NonNls
	static final String STATE_NAME = "DodonaAccounts";
	
	@NonNls
	static final String STATE_STORAGE = "dodona.xml";
	
	@javax.annotation.Nullable
	private DodonaAccount account;
	
	private final PasswordSafe passwordSafe;
	
	/**
	 * DodonaAccountManager constructor.
	 *
	 * @param passwordSafe the password storage
	 */
	public DodonaAccountManager(final PasswordSafe passwordSafe) {
		this.passwordSafe = passwordSafe;
	}
	
	/**
	 * Creates a new DodonaAccount instance.
	 *
	 * @param server the server
	 * @param user   the Dodona user
	 * @return the account
	 */
	@Nonnull
	public static DodonaAccount createAccount(final DodonaServer server,
	                                          final User user) {
		@NonNls final String fullName = user.getFirstName() + " " + user.getLastName();
		return new DodonaAccount(user.getEmail(), fullName, server.getUrl());
	}
	
	/**
	 * Creates credential attributes for the given account, allowing storage in
	 * the Password Safe.
	 *
	 * @param acc the account
	 * @return the credential attributes
	 */
	@Nonnull
	private CredentialAttributes createCredentialAttributes(final DodonaAccount acc) {
		return new CredentialAttributes(
			CredentialAttributesKt.generateServiceName(DodonaBundle.NAME, acc.getId())
		);
	}
	
	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	@Nonnull
	public Optional<DodonaAccount> getAccount() {
		return Optional.ofNullable(this.account);
	}
	
	@Nullable
	@Override
	public DodonaAccount getState() {
		return this.account;
	}
	
	@Override
	public void loadState(@NotNull final DodonaAccount dodonaAccount) {
		this.account = dodonaAccount;
	}
	
	/**
	 * Sets the account.
	 *
	 * @param account the account to set
	 */
	public void setAccount(@Nullable final DodonaAccount account) {
		if (this.account != null && !this.account.equals(account)) {
			// Purge the old account token.
			this.setToken(this.account, null);
		}
		
		// Set the new account.
		this.account = account;
	}
	
	/**
	 * Securely sets the token for the given account.
	 *
	 * @param account the account to set the token for
	 * @param token   the token to set, null to remove it
	 */
	public void setToken(final DodonaAccount account,
	                     @javax.annotation.Nullable final String token) {
		this.passwordSafe.set(
			this.createCredentialAttributes(account),
			token != null ? new Credentials(account.getId(), token) : null
		);
	}
}
