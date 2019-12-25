/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorFactory;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.AccountListener;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccount;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccountManager;
import io.github.thepieterdc.dodona.plugin.authentication.ui.DodonaLoginDialog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;

/**
 * Handles authentication of accounts.
 */
public final class DodonaAuthenticator {
	private final DodonaAccountManager accountManager;
	private final DodonaExecutorHolder executorHolder;
	
	/**
	 * DodonaAuthenticator constructor.
	 */
	private DodonaAuthenticator() {
		this.accountManager = ServiceManager.getService(DodonaAccountManager.class);
		this.executorHolder = new DodonaExecutorHolder(this.createDefaultExecutor());
		
		// Listen for changes to accounts and update the executor accordingly.
		ApplicationManager.getApplication().getMessageBus().connect().subscribe(
			AccountListener.UPDATED_TOPIC,
			() -> this.executorHolder.update(this.createDefaultExecutor())
		);
	}
	
	/**
	 * Creates an executor for the default Dodona account.
	 *
	 * @return the executor
	 */
	@Nonnull
	private DodonaExecutor createDefaultExecutor() {
		return this.accountManager.getAccount().flatMap(account ->
			this.accountManager.getToken().map(token ->
				DodonaExecutorFactory.create(account.getServer(), token)
			)
		).orElseGet(DodonaExecutorFactory::createMissing);
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
	 * Gets an executor for the default Dodona account.
	 *
	 * @return the executor
	 */
	@Nonnull
	public DodonaExecutorHolder getExecutor() {
		return this.executorHolder;
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
	
	/**
	 * Shows a dialog requesting the user to authenticate.
	 *
	 * @param project current active project
	 * @param parent  parent window
	 */
	public void requestAuthentication(@Nullable final Project project,
	                                  @Nullable final Component parent) {
		final DodonaLoginDialog dialog = new DodonaLoginDialog(project, parent);
		if (dialog.showAndGet()) {
			// Create a new account.
			final DodonaAccount account = DodonaAccountManager
				.createAccount(dialog.getServer(), dialog.getUser());
			
			// Store the account in the account manager.
			this.accountManager.setAccount(account, dialog.getToken());
		}
	}
}
