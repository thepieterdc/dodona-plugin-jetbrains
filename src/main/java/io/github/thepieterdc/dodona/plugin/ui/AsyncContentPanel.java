/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui;

import com.intellij.ui.ScrollPaneFactory;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.panels.UnauthenticatedPanel;
import io.github.thepieterdc.dodona.plugin.ui.util.PanelUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import javax.swing.*;
import java.awt.*;

import static io.github.thepieterdc.dodona.plugin.ui.util.PanelUtils.showCard;

/**
 * A panel of which the content is asynchronously loaded.
 *
 * @param <C> type of the content panel
 */
public class AsyncContentPanel<C extends Component> extends JPanel {
	@NonNls
	private static final String CARD_CONTENT = "ASYNC_CONTENT";
	@NonNls
	private static final String CARD_LOADING = "ASYNC_LOADING";
	@NonNls
	private static final String CARD_UNAUTHENTICATED = "ASYNC_UNAUTHENTICATED";
	
	protected final C content;
	private final boolean scrollContent;
	
	/**
	 * AsyncContentPanel constructor.
	 *
	 * @param content        the content panel
	 * @param loadingTextKey the key to the loading text to display underneath
	 *                       the spinner
	 */
	public AsyncContentPanel(final C content,
	                         @PropertyKey(resourceBundle = DodonaBundle.BUNDLE_NAME) final String loadingTextKey) {
		this(content, loadingTextKey, false);
	}
	
	/**
	 * AsyncContentPanel constructor.
	 *
	 * @param content        the content panel
	 * @param loadingTextKey the key to the loading text to display underneath
	 *                       the spinner
	 * @param scrollContent  true to wrap the content in a scrollpane
	 */
	public AsyncContentPanel(final C content,
	                         @PropertyKey(resourceBundle = DodonaBundle.BUNDLE_NAME) final String loadingTextKey,
	                         final boolean scrollContent) {
		super(new CardLayout(0, 0));
		this.content = content;
		this.scrollContent = scrollContent;
		this.initialize(loadingTextKey);
	}
	
	/**
	 * Adds the cards.
	 *
	 * @param loadingTextKey the key to the loading text to display underneath
	 *                       the spinner
	 */
	private void initialize(@PropertyKey(resourceBundle = DodonaBundle.BUNDLE_NAME) final String loadingTextKey) {
		// Create a loading card.
		final JScrollPane loading = PanelUtils.createLoading(this, this.content.getClass(), loadingTextKey);
		
		// Create an unauthenticated card.
		final JScrollPane unauthenticated = new UnauthenticatedPanel().wrap();
		
		// Add the content card.
		if (this.scrollContent) {
			this.add(
				ScrollPaneFactory.createScrollPane(this.content, true),
				CARD_CONTENT
			);
		} else {
			this.add(this.content, CARD_CONTENT);
		}
		
		// Add the loading card.
		this.add(loading, CARD_LOADING);
		
		// Add the unauthenticated card.
		this.add(unauthenticated, CARD_UNAUTHENTICATED);
		
		// Show the loading card.
		showCard(this, CARD_LOADING);
	}
	
	/**
	 * Shows the card that contains the content.
	 */
	protected void showContentCard() {
		showCard(this, CARD_CONTENT);
	}
	
	/**
	 * Shows the card that contains the authentication failure.
	 */
	protected void showUnauthenticatedCard() {
		showCard(this, CARD_UNAUTHENTICATED);
	}
}
