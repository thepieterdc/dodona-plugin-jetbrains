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
import com.intellij.util.ui.AsyncProcessIcon;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

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
	 * Creates a loading card.
	 *
	 * @param parent         the parent component
	 * @param parentClass    the class of the parent
	 * @param loadingTextKey the key to the loading text to display underneath
	 *                       the spinner
	 * @return the created card
	 */
	@Nonnull
	public static JScrollPane createLoadingCard(final JPanel parent,
	                                            final Class<?> parentClass,
	                                            @PropertyKey(resourceBundle = DodonaBundle.BUNDLE_NAME) final String loadingTextKey) {
		final AsyncProcessIcon loadingIcon = new AsyncProcessIcon.Big(parentClass + ".loading");
		loadingIcon.setBackground(parent.getBackground());
		
		final JPanel loadingInnerPanel = new JPanel(new BorderLayout(10, 10));
		loadingInnerPanel.add(
			new JLabel(DodonaBundle.message(loadingTextKey)),
			BorderLayout.PAGE_END
		);
		loadingInnerPanel.add(loadingIcon, BorderLayout.CENTER);
		
		final JPanel loadingPanel = new JPanel(new GridBagLayout());
		loadingPanel.add(loadingInnerPanel, new GridBagConstraints());
		return ScrollPaneFactory.createScrollPane(loadingPanel, true);
	}
	
	/**
	 * Adds the cards.
	 *
	 * @param loadingTextKey the key to the loading text to display underneath
	 *                       the spinner
	 */
	private void initialize(@PropertyKey(resourceBundle = DodonaBundle.BUNDLE_NAME) final String loadingTextKey) {
		// Create a loading card.
		final JScrollPane loadingCard = createLoadingCard(this, this.content.getClass(), loadingTextKey);
		
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
		this.add(loadingCard, CARD_LOADING);
		
		// Show the loading card.
		showCard(this, CARD_LOADING);
	}
	
	/**
	 * Shows the card with the given name in the parent panel.
	 *
	 * @param parent the container to show the card in
	 * @param card   the card to show
	 */
	public static void showCard(final JPanel parent,
	                            @NonNls final String card) {
		((CardLayout) parent.getLayout()).show(parent, card);
	}
	
	/**
	 * Shows the card that contains the content.
	 */
	protected void showContentCard() {
		showCard(this, CARD_CONTENT);
	}
}
