/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.tabs;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Common tab.
 */
abstract class AbstractTab {
	private final String title;
	
	/**
	 * AbstractTab constructor.
	 *
	 * @param title title of the tab
	 */
	protected AbstractTab(@Nls(capitalization = Nls.Capitalization.Title) final String title) {
		super();
		this.title = title;
	}
	
	/**
	 * Creates the content pane.
	 *
	 * @return the content pane
	 */
	@Nonnull
	abstract JComponent createContent();
	
	/**
	 * Appends the tab to the tool window.
	 *
	 * @param toolWindow the tool window
	 */
	public void setup(final ToolWindow toolWindow) {
		final ContentManager contentMgr = toolWindow.getContentManager();
		
		final JComponent component = this.createContent();
		
		final Content content = contentMgr.getFactory().createContent(
			component,
			this.title,
			false
		);
		content.setCloseable(false);
		content.setPreferredFocusableComponent(component);
		
		contentMgr.addContent(content);
	}
}