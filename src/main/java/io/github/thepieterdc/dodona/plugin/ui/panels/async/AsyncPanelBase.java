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
import io.github.thepieterdc.dodona.plugin.api.executor.ExecutorListener;
import io.github.thepieterdc.dodona.plugin.ui.Updatable;
import io.github.thepieterdc.dodona.plugin.ui.cards.LoadingCard;
import io.github.thepieterdc.dodona.plugin.ui.cards.NoConnectionCard;
import io.github.thepieterdc.dodona.plugin.ui.panels.CardPanelBase;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.concurrent.CompletableFuture;

/**
 * A panel of which the content is asynchronously loaded.
 *
 * @param <T> type class of the content
 */
abstract class AsyncPanelBase<T> extends CardPanelBase implements Disposable, Updatable {
	@NonNls
	private static final String CARD_LOADING = "ASYNC_LOADING";
	
	/**
	 * AsyncPanel constructor.
	 *
	 * @param project     current active project
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected AsyncPanelBase(@Nullable final Project project,
	                         @Nls final String loadingText) {
		super(project);
		this.initialize(loadingText);
		
		// Refresh the contents when the default executor is updated.
		ApplicationManager.getApplication().getMessageBus()
			.connect(this)
			.subscribe(ExecutorListener.UPDATED_TOPIC, this::requestUpdate);
	}
	
	@Nonnull
	@Override
	protected NoConnectionCard createNoConnectionCard() {
		return NoConnectionCard.create(this);
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
	 * Adds the cards.
	 *
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected void initialize(@Nls final String loadingText) {
		// Create a loading card.
		final JScrollPane loading = LoadingCard.create(loadingText, this)
			.wrap();
		
		// Add the loading card.
		this.add(loading, CARD_LOADING);
	}
	
	/**
	 * Shows the card that contains the loading screen.
	 */
	protected void showLoadingCard() {
		this.showCard(CARD_LOADING);
	}
}
