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
 * @param <C> type class of the content pane
 * @param <T> type class of the content
 */
public abstract class StaticAsyncPanel<T, C extends Component & AsyncPanelContent<T>>
	extends AsyncPanelBase<T> {
	
	@Nullable
	private C content;
	
	/**
	 * AsyncPanel constructor.
	 *
	 * @param project     current active project
	 * @param loadingText the loading text to display underneath the spinner
	 */
	protected StaticAsyncPanel(@Nullable final Project project,
	                           @Nls final String loadingText) {
		super(project, loadingText);
	}
	
	/**
	 * Creates the content pane.
	 *
	 * @return the content pane
	 */
	@Nonnull
	protected abstract C createContentPane();
	
	@Override
	public void requestUpdate() {
		// Show the loading card.
		this.showLoadingCard();
		
		// Create the content pane.
		if (this.content == null) {
			this.content = this.createContentPane();
			this.add(this.content, CARD_CONTENT);
		}
		
		// Fetch the new data.
		this.getData().whenComplete((data, error) ->
			SwingUtilities.invokeLater(() -> {
				if (error != null) {
					this.handleError(error);
				} else {
					this.content.update(data);
					this.showContentCard();
				}
			})
		);
	}
}
