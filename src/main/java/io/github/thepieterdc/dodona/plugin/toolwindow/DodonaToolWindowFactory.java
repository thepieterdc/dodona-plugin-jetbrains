/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.DeadlinesTab;
import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.SubmissionsTab;
import org.jetbrains.annotations.NotNull;

/**
 * Manages the tool window.
 */
public class DodonaToolWindowFactory implements DumbAware, ToolWindowFactory {
	private static final int TW_ICON_SIZE = 13;
	
	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		// Get a request executor.
		final DodonaExecutorHolder executor =
			DodonaAuthenticator.getInstance().getExecutor();
		
		// Set the title and auto close property.
		toolWindow.setTitle(DodonaBundle.NAME);
		toolWindow.setToHideOnEmptyContent(true);
		
		// Create the deadlines tab.
		final DeadlinesTab deadlines = new DeadlinesTab(project, executor);
		Disposer.register(project, deadlines);
		
		// Create the submissions tab.
		final SubmissionsTab submissions = new SubmissionsTab(
			project,
			executor
		);
		Disposer.register(project, submissions);
		
		// Set the ToolWindow.
		toolWindow.setTitle(DodonaBundle.message("toolwindow.title"));
		
		// Append all tabs to the content.
		deadlines.setup(toolWindow);
		submissions.setup(toolWindow);
	}
	
	@Override
	public boolean shouldBeAvailable(@NotNull Project project) {
		return DodonaProjectSettings.getInstance(project)
			.getCourseId()
			.isPresent();
	}
}
