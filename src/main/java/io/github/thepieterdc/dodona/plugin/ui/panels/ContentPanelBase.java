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
import com.intellij.ui.ScrollPaneFactory;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * A panel of cards with a fixed content pane.
 */
public abstract class ContentPanelBase<T extends Component> extends CardPanelBase {
	/**
	 * ContentPanelBase constructor.
	 *
	 * @param project current active project
	 */
	protected ContentPanelBase(final Project project) {
		super(project);
	}
	
	/**
	 * Creates the content card.
	 *
	 * @return the card
	 */
	@Nonnull
	protected abstract T createContentCard();
	
	/**
	 * Adds the cards.
	 */
	protected void initialize() {
		// Create a content card.
		final JScrollPane content = ScrollPaneFactory.createScrollPane(
			this.createContentCard(), true
		);
		
		// Add the card.
		this.add(content, CARD_CONTENT);
		this.showContentCard();
	}
}
