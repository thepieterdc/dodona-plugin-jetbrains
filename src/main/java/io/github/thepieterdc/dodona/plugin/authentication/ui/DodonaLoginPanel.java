/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.ui;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.panels.Wrapper;
import com.intellij.util.ui.UI;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.exceptions.AuthenticationException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutorFactory;
import io.github.thepieterdc.dodona.plugin.api.DodonaFuture;
import io.github.thepieterdc.dodona.plugin.api.DodonaServer;
import io.github.thepieterdc.dodona.plugin.ui.util.Validators;
import io.github.thepieterdc.dodona.resources.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Panel that contains a server and API token field.
 */
public final class DodonaLoginPanel extends Wrapper {
	@Nullable
	private final Project project;
	
	@Nullable
	private ValidationInfo authError;
	
	private final ComboBox<DodonaServer> cbServer;
	private final JBPasswordField pwdToken;
	
	/**
	 * DodonaLoginPanel constructor.
	 *
	 * @param project the current project
	 */
	public DodonaLoginPanel(@Nullable final Project project) {
		this.cbServer = new ComboBox<>(DodonaServer.values(), 0);
		this.project = project;
		this.pwdToken = new JBPasswordField();
		
		this.setContent(this.createCenter());
		this.cbServer.setSelectedItem(DodonaClient.DEFAULT_HOST);
		this.pwdToken.requestFocus();
	}
	
	/**
	 * Attempts to authenticate the server and token.
	 *
	 * @param indicator progress indicator
	 * @return the authenticated user if successful
	 */
	CompletableFuture<User> authenticate(final ProgressIndicator indicator) {
		this.setBusy(true);
		this.authError = null;
		
		// Get the input values.
		final DodonaServer server = this.getServer();
		final String token = this.getToken();
		
		// Get an executor.
		final DodonaExecutor executor = DodonaExecutorFactory.create(server, token);
		
		// Send the request.
		return executor
			.execute(this.project, DodonaBundle.message("auth.dialog.authenticating"), true, indicator, DodonaClient::me)
			.whenCompleteRunOnEdt((user, err) -> {
				this.setBusy(false);
				if (err != null && !DodonaFuture.cancelled(err)) {
					this.authError = this.handleError(err);
				}
			});
	}
	
	/**
	 * Creates the center panel.
	 *
	 * @return the center panel
	 */
	@Nonnull
	private JPanel createCenter() {
		return UI.PanelFactory.grid()
			.add(UI.PanelFactory.panel(this.cbServer).withLabel(DodonaBundle.message("auth.dialog.server")))
			.add(UI.PanelFactory.panel(this.pwdToken).withLabel(DodonaBundle.message("auth.dialog.token")))
			.createPanel();
	}
	
	/**
	 * Gets the Dodona server.
	 *
	 * @return the server
	 */
	@Nonnull
	DodonaServer getServer() {
		return this.cbServer.getItemAt(this.cbServer.getSelectedIndex());
	}
	
	/**
	 * Gets the API token.
	 *
	 * @return the API token
	 */
	@Nonnull
	String getToken() {
		return String.valueOf(this.pwdToken.getPassword()).trim();
	}
	
	/**
	 * Gets the preferred focus element.
	 *
	 * @return the preferred focus field
	 */
	@Nonnull
	JComponent getPreferredFocus() {
		return this.pwdToken;
	}
	
	/**
	 * Handles the given authentication error.
	 *
	 * @param error the error to handle
	 * @return the error message
	 */
	@Nonnull
	private ValidationInfo handleError(final Throwable error) {
		if (error instanceof UnknownHostException) {
			return new ValidationInfo(DodonaBundle.message("auth.error.unreachable")).withOKEnabled();
		}
		
		if (error instanceof ConnectException) {
			return new ValidationInfo(DodonaBundle.message("auth.error.unreachable")).withOKEnabled();
		}
		
		if (error instanceof AuthenticationException) {
			return new ValidationInfo(error.getMessage()).withOKEnabled();
		}
		
		if (error.getCause() != null) {
			return this.handleError(error.getCause());
		}
		
		return new ValidationInfo(DodonaBundle.message("auth.error.custom", error.getMessage())).withOKEnabled();
	}
	
	/**
	 * Validates all input fields.
	 *
	 * @return validation results
	 */
	List<ValidationInfo> doValidateAll() {
		return Stream
			.of(Validators.notEmpty(this.pwdToken, DodonaBundle.message("auth.error.token_empty")),
				this.authError)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}
	
	/**
	 * Sets the working status.
	 *
	 * @param busy true if an action is being executed
	 */
	private void setBusy(final boolean busy) {
		this.cbServer.setEnabled(!busy);
		this.pwdToken.setEnabled(!busy);
	}
}
