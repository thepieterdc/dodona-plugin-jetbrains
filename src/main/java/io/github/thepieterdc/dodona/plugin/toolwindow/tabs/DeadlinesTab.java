/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.tabs;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines.DeadlinesPanel;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Controller for the tab showing upcoming deadlines
 */
public class DeadlinesTab extends AbstractTab implements Disposable {
	private static final String TAB_TITLE = DodonaBundle.message("toolwindow.deadlines.title");
	
	private final DeadlinesPanel deadlinesPanel;
	
	/**
	 * DeadlinesTab constructor.
	 *
	 * @param project  the current project
	 * @param executor request executor
	 */
	public DeadlinesTab(final Project project,
	                    final DodonaExecutorHolder executor) {
		super(TAB_TITLE);
		this.deadlinesPanel = new DeadlinesPanel(project, executor);
		
		// Load the deadlines.
		this.deadlinesPanel.requestUpdate();
	}
	
	@Nonnull
	@Override
	JComponent createContent() {
		return this.deadlinesPanel;
	}
	
	@Override
	public void dispose() {
		Disposer.dispose(this.deadlinesPanel);
	}
}
