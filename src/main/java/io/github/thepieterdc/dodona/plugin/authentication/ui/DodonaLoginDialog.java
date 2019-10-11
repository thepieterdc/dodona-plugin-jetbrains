/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.ui;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.EmptyProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.util.ui.JBUI;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaFuture;
import io.github.thepieterdc.dodona.plugin.api.DodonaServer;
import io.github.thepieterdc.dodona.resources.User;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Dialog that prompts the user to authenticate.
 */
public final class DodonaLoginDialog extends DialogWrapper {
	@NonNls
	private static final String API_TOKEN_URL = "https://dodona-edu.github.io/guides/creating-an-api-token/";
	
	private final DodonaLoginPanel loginPanel;
	
	private DodonaServer server;
	private String token;
	private User user;
	
	/**
	 * DodonaLoginDialog constructor.
	 *
	 * @param project the current project
	 * @param parent  parent component
	 */
	DodonaLoginDialog(@Nullable final Project project,
	                  @Nullable final Component parent) {
		super(project, parent, false, IdeModalityType.PROJECT);
		
		this.loginPanel = new DodonaLoginPanel(project);
		
		this.setOKButtonText(DodonaBundle.message("auth.dialog.sign_in"));
		this.setTitle(DodonaBundle.message("auth.dialog.title"));
		this.init();
	}
	
	@Nonnull
	@Override
	protected JComponent createCenterPanel() {
		return this.loginPanel;
	}
	
	@Nonnull
	@Override
	protected JPanel createSouthAdditionalPanel() {
		return JBUI.Panels.simplePanel()
			.addToCenter(LinkLabel.create(
				DodonaBundle.message("auth.dialog.instructions"),
				() -> BrowserUtil.browse(DodonaLoginDialog.API_TOKEN_URL)))
			.addToRight(new JBLabel(AllIcons.Ide.External_link_arrow));
	}
	
	@Override
	protected void doOKAction() {
		final EmptyProgressIndicator indicator = new EmptyProgressIndicator(ModalityState.stateForComponent(this.loginPanel));
		Disposer.register(this.myDisposable, indicator::cancel);
		DodonaFuture.handleOnEdt(this.loginPanel.authenticate(indicator), (u, err) -> {
			if (err != null && !DodonaFuture.cancelled(err)) {
				this.startTrackingValidation();
			} else if (u != null) {
				this.server = this.loginPanel.getServer();
				this.token = this.loginPanel.getToken();
				this.user = u;
				this.close(DialogWrapper.OK_EXIT_CODE, true);
			}
		});
	}
	
	@NotNull
	@Override
	protected List<ValidationInfo> doValidateAll() {
		return this.loginPanel.doValidateAll();
	}
	
	@Override
	public JComponent getPreferredFocusedComponent() {
		return this.loginPanel.getPreferredFocus();
	}
	
	/**
	 * Gets the server url.
	 *
	 * @return the server
	 */
	@Nonnull
	public DodonaServer getServer() {
		return this.server;
	}
	
	/**
	 * Gets the authentication token.
	 *
	 * @return the authentication token
	 */
	public String getToken() {
		return this.token;
	}
	
	/**
	 * Gets the authenticated user.
	 *
	 * @return the authenticated user
	 */
	@Nonnull
	public User getUser() {
		return this.user;
	}
}