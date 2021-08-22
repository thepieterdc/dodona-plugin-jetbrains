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
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.messages.MessageBus;
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
	
	private final MessageBus bus;
	
	/**
	 * DodonaAccountManager constructor.
	 */
	public DodonaAccountManager() {
		this.bus = ApplicationManager.getApplication().getMessageBus();
	}
	
	/**
	 * Clears the account and authentication token.
	 */
	public void clearAccount() {
		// Clear the token if there is an account set.
		Optional.ofNullable(this.account).ifPresent(acc -> PasswordSafe.getInstance().set(
			this.createCredentialAttributes(acc),
			null
		));
		
		// Ensure that there was an account.
		final boolean hadAccount = this.account != null;
		
		// Clear the account.
		this.account = null;
		
		// Broadcast the removal of the account.
		if (hadAccount) {
			this.bus.syncPublisher(AccountListener.UPDATED_TOPIC).updated();
		}
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
	
	/**
	 * Gets a shared instance of the account manager.
	 *
	 * @return the instance
	 */
	@Nonnull
	public static DodonaAccountManager getInstance() {
		return ApplicationManager.getApplication().getService(DodonaAccountManager.class);
	}
	
	@Nullable
	@Override
	public DodonaAccount getState() {
		return this.account;
	}
	
	/**
	 * Gets the token for the current account, if available.
	 */
	@Nonnull
	public Optional<String> getToken() {
		return this.getAccount()
			.map(this::createCredentialAttributes)
			.map(PasswordSafe.getInstance()::get)
			.map(Credentials::getPasswordAsString);
	}
	
	/**
	 * Validates whether the given account is already in the account list.
	 *
	 * @param acc the account to check
	 * @return true if the account exists
	 */
	public boolean hasAccount(final DodonaAccount acc) {
		return acc.equals(this.account);
	}
	
	@Override
	public void loadState(@NotNull final DodonaAccount dodonaAccount) {
		this.account = dodonaAccount;
	}
	
	/**
	 * Sets the account.
	 *
	 * @param account the account to set
	 * @param token   the corresponding authentication token
	 */
	public void setAccount(final DodonaAccount account,
	                       final String token) {
		// Remove the previous account if set.
		this.clearAccount();
		
		// Set the new account.
		this.account = account;
		
		// Set the new account token.
		PasswordSafe.getInstance().set(
			this.createCredentialAttributes(account),
			new Credentials(account.getId(), token)
		);
		
		// Broadcast the new account.
		this.bus.syncPublisher(AccountListener.UPDATED_TOPIC).updated();
	}
}
