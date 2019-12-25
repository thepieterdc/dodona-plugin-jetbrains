/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels.async;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

/**
 * An async panel of which the content pane is recreated after every update.
 *
 * @param <T> type class of the content
 */
public abstract class DynamicAsyncPanel<T> extends AsyncPanelBase<T> {
	@Nullable
	private Component content;
	
	/**
	 * DynamicAsyncPanel constructor.
	 *
	 * @param project     current active project
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected DynamicAsyncPanel(@Nullable final Project project,
	                            @Nls final String loadingText) {
		super(project, loadingText);
	}
	
	/**
	 * Creates the content pane.
	 *
	 * @param data the data to show in the pane
	 * @return the content pane
	 */
	@Nonnull
	protected abstract Component createContentPane(final T data);
	
	/**
	 * Requests an update of the content.
	 */
	public void requestUpdate() {
		// Show the loading card.
		this.showLoadingCard();
		
		// Remove the previous content card if it exists.
		if (this.content != null) {
			this.remove(this.content);
		}
		
		// Fetch the new data.
		this.getData().whenComplete((data, error) ->
			SwingUtilities.invokeLater(() -> {
				if (error != null) {
					this.handleError(error);
				} else {
					// Create the content pane.
					this.content = this.createContentPane(data);
					this.add(CARD_CONTENT, this.content);
					this.showContentCard();
				}
			})
		);
	}
}
