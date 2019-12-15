/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels.async;

import io.github.thepieterdc.dodona.exceptions.AuthenticationException;
import io.github.thepieterdc.dodona.plugin.ui.panels.LoadingPanel;
import io.github.thepieterdc.dodona.plugin.ui.panels.NoConnectionPanel;
import io.github.thepieterdc.dodona.plugin.ui.panels.UnauthenticatedPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * A panel of which the content is asynchronously loaded.
 *
 * @param <T> type class of the content
 */
abstract class AsyncPanelBase<T> extends JPanel {
	@NonNls
	static final String CARD_CONTENT = "ASYNC_CONTENT";
	@NonNls
	private static final String CARD_LOADING = "ASYNC_LOADING";
	@NonNls
	private static final String CARD_NO_CONNECTION = "ASYNC_NO_CONNECTION";
	@NonNls
	private static final String CARD_UNAUTHENTICATED = "ASYNC_UNAUTHENTICATED";
	
	/**
	 * AsyncPanel constructor.
	 *
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected AsyncPanelBase(@Nls final String loadingText) {
		super(new CardLayout(0, 0));
		this.initialize(loadingText);
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
		} else if (error instanceof IOException) {
			this.showNoConnectionCard();
		} else if (error.getCause() != null) {
			this.handleError(error.getCause());
		} else {
			throw new RuntimeException(error);
		}
	}
	
	/**
	 * Adds the cards.
	 *
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected void initialize(@Nls final String loadingText) {
		// Create a loading card.
		final JScrollPane loading = LoadingPanel.create(loadingText, this)
			.wrap();
		
		// Create a no connection card.
		final JScrollPane noConnection = NoConnectionPanel.create().wrap();
		
		// Create an unauthenticated card.
		final JScrollPane unauthenticated = UnauthenticatedPanel.create().wrap();
		
		// Add all cards.
		this.add(loading, CARD_LOADING);
		this.add(noConnection, CARD_NO_CONNECTION);
		this.add(unauthenticated, CARD_UNAUTHENTICATED);
	}
	
	/**
	 * Requests an update of the content.
	 */
	public abstract void requestUpdate();
	
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
