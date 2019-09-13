/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication;

import com.intellij.openapi.components.ServiceManager;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutorFactory;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccount;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccountManager;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Handles authentication of accounts.
 */
public class DodonaAuthenticator {
	private final DodonaAccountManager accountManager;
	
	/**
	 * DodonaAuthenticator constructor.
	 *
	 * @param accountManager account manager
	 */
	private DodonaAuthenticator(final DodonaAccountManager accountManager) {
		this.accountManager = accountManager;
	}
	
	/**
	 * Gets the account if available.
	 *
	 * @return the account
	 */
	@Nonnull
	public Optional<DodonaAccount> getAccount() {
		return this.accountManager.getAccount();
	}
	
	/**
	 * Gets an executor for the default Dodona account if it's available.
	 *
	 * @return the executor
	 */
	public Optional<DodonaExecutor> getExecutor() {
		return this.accountManager.getAccount().flatMap(account ->
			this.accountManager.getToken(account).map(token ->
				DodonaExecutorFactory.create(account.getServer(), token)
			)
		);
	}
	
	/**
	 * Gets a shared instance of the authenticator.
	 *
	 * @return the instance
	 */
	@Nonnull
	public static DodonaAuthenticator getInstance() {
		return ServiceManager.getService(DodonaAuthenticator.class);
	}
}
