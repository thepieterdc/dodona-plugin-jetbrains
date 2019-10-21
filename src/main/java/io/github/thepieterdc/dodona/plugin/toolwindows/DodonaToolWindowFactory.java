/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindows;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.toolwindows.tabs.DeadlinesTab;
import org.jetbrains.annotations.NotNull;

/**
 * Creates the tool window.
 */
public class DodonaToolWindowFactory implements DumbAware, ToolWindowFactory {
	@Override
	public void createToolWindowContent(@NotNull final Project project,
	                                    @NotNull final ToolWindow toolWindow) {
		// Get a request executor.
		final DodonaExecutor executor =
			DodonaAuthenticator.getInstance().getExecutor();
		
		// Get the project settings.
		final DodonaProjectSettings settings =
			DodonaProjectSettings.getInstance(project);
		
		// Set the title and auto close property.
		toolWindow.setTitle(DodonaBundle.NAME);
		toolWindow.setToHideOnEmptyContent(true);
		
		// Create the deadlines tab.
		final DeadlinesTab deadlines = new DeadlinesTab(
			executor,
			settings.getCourseId().orElse(0L)
		);
		
		// Append all tabs to the content.
		toolWindow.getContentManager().addContent(deadlines.getContent());
	}
}
