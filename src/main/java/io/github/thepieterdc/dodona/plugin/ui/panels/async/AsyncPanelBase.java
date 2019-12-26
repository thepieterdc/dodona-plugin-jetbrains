/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels.async;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.exceptions.AuthenticationException;
import io.github.thepieterdc.dodona.exceptions.accessdenied.ResourceAccessDeniedException;
import io.github.thepieterdc.dodona.exceptions.notfound.ResourceNotFoundException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.ExecutorListener;
import io.github.thepieterdc.dodona.plugin.exceptions.DodonaPluginException;
import io.github.thepieterdc.dodona.plugin.ui.Updatable;
import io.github.thepieterdc.dodona.plugin.ui.panels.ErrorPanel;
import io.github.thepieterdc.dodona.plugin.ui.panels.LoadingPanel;
import io.github.thepieterdc.dodona.plugin.ui.panels.NoConnectionPanel;
import io.github.thepieterdc.dodona.plugin.ui.panels.UnauthenticatedPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * A panel of which the content is asynchronously loaded.
 *
 * @param <T> type class of the content
 */
abstract class AsyncPanelBase<T> extends JPanel implements Disposable, Updatable {
	@NonNls
	static final String CARD_CONTENT = "ASYNC_CONTENT";
	@NonNls
	private static final String CARD_ERROR = "ASYNC_ERROR";
	@NonNls
	private static final String CARD_LOADING = "ASYNC_LOADING";
	@NonNls
	private static final String CARD_NO_CONNECTION = "ASYNC_NO_CONNECTION";
	@NonNls
	private static final String CARD_UNAUTHENTICATED = "ASYNC_UNAUTHENTICATED";
	
	@Nullable
	private final Project project;
	
	/**
	 * AsyncPanel constructor.
	 *
	 * @param project     current active project
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected AsyncPanelBase(@Nullable final Project project,
	                         @Nls final String loadingText) {
		super(new CardLayout(0, 0));
		this.project = project;
		this.initialize(loadingText);
		
		// Refresh the contents when the default executor is updated.
		ApplicationManager.getApplication().getMessageBus()
			.connect(this)
			.subscribe(ExecutorListener.UPDATED_TOPIC, this::requestUpdate);
	}
	
	@Override
	public void dispose() {
		// Required to dispose the message bus when this panel is no longer
		// visible.
	}
	
	/**
	 * Gets the data to display in the content pane.
	 *
	 * @return request to fetch the data
	 */
	@Nonnull
	protected abstract CompletableFuture<T> getData();
	
	/**
	 * Handles the given error.
	 *
	 * @param error the error to handle
	 */
	void handleError(final Throwable error) {
		if (error instanceof AuthenticationException) {
			this.showUnauthenticatedCard();
		} else if (error instanceof ResourceAccessDeniedException) {
			this.showErrorCard();
		} else if (error instanceof ResourceNotFoundException) {
			this.showErrorCard();
		} else if (error instanceof IOException) {
			this.showNoConnectionCard();
		} else if (error.getCause() != null) {
			this.handleError(error.getCause());
		} else {
			throw new DodonaPluginException(error.getMessage(), error);
		}
	}
	
	/**
	 * Adds the cards.
	 *
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected void initialize(@Nls final String loadingText) {
		// Create an error card.
		final JScrollPane error = new ErrorPanel(
			DodonaBundle.message("panel.error.message")).wrap();
		
		// Create a loading card.
		final JScrollPane loading = LoadingPanel.create(loadingText, this)
			.wrap();
		
		// Create a no connection card.
		final JScrollPane noConnection = NoConnectionPanel.create(this).wrap();
		
		// Create an unauthenticated card.
		final JScrollPane unauthenticated = UnauthenticatedPanel
			.create(this.project, this)
			.wrap();
		
		// Add all cards.
		this.add(error, CARD_ERROR);
		this.add(loading, CARD_LOADING);
		this.add(noConnection, CARD_NO_CONNECTION);
		this.add(unauthenticated, CARD_UNAUTHENTICATED);
	}
	
	/**
	 * Displays the card with the given name.
	 *
	 * @param name the name of the card to display
	 */
	protected void showCard(@NonNls final String name) {
		((CardLayout) this.getLayout()).show(this, name);
	}
	
	/**
	 * Shows the card that contains the content pane.
	 */
	protected void showContentCard() {
		this.showCard(CARD_CONTENT);
	}
	
	/**
	 * Shows the card that an error message.
	 */
	void showErrorCard() {
		this.showCard(CARD_ERROR);
	}
	
	/**
	 * Shows the card that contains the loading screen.
	 */
	protected void showLoadingCard() {
		this.showCard(CARD_LOADING);
	}
	
	/**
	 * Shows the card that contains the connection failure.
	 */
	void showNoConnectionCard() {
		this.showCard(CARD_NO_CONNECTION);
	}
	
	/**
	 * Shows the card that contains the authentication failure.
	 */
	private void showUnauthenticatedCard() {
		this.showCard(CARD_UNAUTHENTICATED);
	}
}
