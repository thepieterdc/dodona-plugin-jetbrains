/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels;

import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.exceptions.AuthenticationException;
import io.github.thepieterdc.dodona.exceptions.accessdenied.ResourceAccessDeniedException;
import io.github.thepieterdc.dodona.exceptions.notfound.ResourceNotFoundException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.exceptions.DodonaPluginException;
import io.github.thepieterdc.dodona.plugin.ui.cards.ErrorCard;
import io.github.thepieterdc.dodona.plugin.ui.cards.NoConnectionCard;
import io.github.thepieterdc.dodona.plugin.ui.cards.UnauthenticatedCard;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * A panel of cards.
 */
public abstract class CardPanelBase extends JPanel {
	@NonNls
	protected static final String CARD_CONTENT = "CARD_CONTENT";
	@NonNls
	protected static final String CARD_ERROR = "CARD_ERROR";
	@NonNls
	protected static final String CARD_NO_CONNECTION = "CARD_NO_CONNECTION";
	@NonNls
	protected static final String CARD_UNAUTHENTICATED = "CARD_UNAUTHENTICATED";
	
	@Nullable
	protected final Project project;
	
	/**
	 * CardPanelBase constructor.
	 *
	 * @param project current active project
	 */
	protected CardPanelBase(@Nullable final Project project) {
		super(new CardLayout(0, 0));
		this.project = project;
		this.initializeCards();
	}
	
	/**
	 * Creates the NoConnection card.
	 * @return the card
	 */
	@Nonnull
	protected NoConnectionCard createNoConnectionCard() {
		return NoConnectionCard.create();
	}
	
	/**
	 * Handles the given error.
	 *
	 * @param error the error to handle
	 */
	protected void handleError(final Throwable error) {
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
	 */
	private void initializeCards() {
		// Create an error card.
		final JScrollPane error = new ErrorCard(
			DodonaBundle.message("card.error.message")).wrap();
		
		// Create a no connection card.
		final JScrollPane noConnection = this.createNoConnectionCard().wrap();
		
		// Create an unauthenticated card.
		final JScrollPane unauthenticated = UnauthenticatedCard
			.create(this.project, this)
			.wrap();
		
		// Add all cards.
		this.add(error, CARD_ERROR);
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
