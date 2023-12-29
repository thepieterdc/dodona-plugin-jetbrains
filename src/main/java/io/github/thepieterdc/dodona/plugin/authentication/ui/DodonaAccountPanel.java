/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.CommonShortcuts;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.util.Pair;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.SideBorder;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.StatusText;
import com.intellij.util.ui.UIUtil;
import com.intellij.util.ui.components.BorderLayoutPanel;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccount;
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccountManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Optional;

/**
 * A list of Dodona accounts. Currently only one account is allowed.
 */
public class DodonaAccountPanel extends BorderLayoutPanel implements Disposable {
	private final CollectionListModel<DodonaAccount> accountListModel;
	private final JBList<DodonaAccount> accountList;

	private boolean modified;

	@Nullable
	private String newToken;

	/**
	 * DodonaAccountPanel constructor.
	 */
	public DodonaAccountPanel() {
		super();
		this.accountListModel = new CollectionListModel<>();
		this.accountList = this.createAccountsList();
		this.modified = false;

		this.newToken = null;

		this.addToCenter(this.createCenterComponent());
	}

	/**
	 * Shows an add account dialog.
	 */
	private void addAccount() {
		final DodonaLoginDialog dialog = new DodonaLoginDialog(null, this);
		if (dialog.showAndGet()) {
			// Create a new account.
			final DodonaAccount account = DodonaAccountManager
				.createAccount(dialog.getServer(), dialog.getUser());

			// Remove the previous account.
			this.removeAccount();

			// Store the token.
			this.newToken = dialog.getToken();

			// Add the new account to the model.
			this.accountListModel.add(account);

			// Set the modification status.
			this.modified = true;
		}
	}

	/**
	 * Clears the modification status.
	 */
	public void clearModified() {
		this.modified = false;
	}

	/**
	 * Clears the map of new authentication data.
	 */
	public void clearNewData() {
		this.newToken = null;
	}

	/**
	 * Creates the list of Dodona accounts.
	 *
	 * @return the list
	 */
	@Nonnull
	private JBList<DodonaAccount> createAccountsList() {
		final DodonaAccountRenderer renderer = new DodonaAccountRenderer();
		final JBList<DodonaAccount> ret = new JBList<>(this.accountListModel);
		ret.setCellRenderer(renderer);
		ret.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Configure the empty behaviour.
		ret.getEmptyText()
			.appendText(DodonaBundle.message("auth.accounts.empty"))
			.appendSecondaryText(DodonaBundle.message("auth.accounts.add"), SimpleTextAttributes.LINK_ATTRIBUTES, e -> this.addAccount())
			.appendSecondaryText(String.format(" (%s)", KeymapUtil.getFirstKeyboardShortcutText(CommonShortcuts.getNew())), StatusText.DEFAULT_ATTRIBUTES, null);
		this.putClientProperty(UIUtil.NOT_IN_HIERARCHY_COMPONENTS, Collections.singletonList(renderer));
		return ret;
	}

	/**
	 * Creates the center component of the panel.
	 *
	 * @return the center component
	 */
	@Nonnull
	private Component createCenterComponent() {
		return ToolbarDecorator
			.createDecorator(this.accountList)
			.disableAddAction()
			.disableUpDownActions()
			.setRemoveAction(btn -> this.removeAccount())
			.setPanelBorder(IdeBorderFactory.createBorder(SideBorder.TOP | SideBorder.BOTTOM))
			.createPanel();
	}

	@Override
	public void dispose() {
		// Not implemented.
	}

	/**
	 * Gets the account and its access token, if the account was newly added.
	 *
	 * @return pair of the account and its associated access token, or an empty
	 * pair if no account is available
	 */
	@Nonnull
	public Pair<DodonaAccount, String> getAccount() {
		// Ensure that there is an account.
		if (this.accountListModel.isEmpty()) {
			return Pair.empty();
		}

		// Return the account and its token.
		return Pair.create(
			this.accountListModel.getElementAt(0),
			this.newToken
		);
	}

	/**
	 * Gets whether the accounts were modified.
	 *
	 * @return true if the accounts were modified
	 */
	public boolean isModified() {
		return this.modified;
	}

	/**
	 * Removes the selected account.
	 */
	private void removeAccount() {
		if (!this.accountListModel.isEmpty()) {
			this.accountListModel.remove(0);
			this.modified = true;
		}
	}

	/**
	 * Sets the account from the settings.
	 */
	public void setAccount(@Nullable final DodonaAccount account) {
		this.accountListModel.removeAll();
		Optional.ofNullable(account).ifPresent(this.accountListModel::add);
	}
}
