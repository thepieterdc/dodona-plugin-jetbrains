/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindows.tabs;

import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
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
	 * Gets the content pane.
	 *
	 * @return the content pane
	 */
	@Nonnull
	public Content getContent() {
		final Content ret = ContentFactory.SERVICE.getInstance().createContent(this.createContent(), this.title, false);
		ret.setCloseable(false);
		return ret;
	}
}