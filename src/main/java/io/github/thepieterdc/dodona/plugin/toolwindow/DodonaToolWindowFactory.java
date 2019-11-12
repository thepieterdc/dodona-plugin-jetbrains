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
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.IconUtil;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.DeadlinesTab;
import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.SubmissionsTab;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Creates the tool window.
 */
public class DodonaToolWindowFactory implements DumbAware {
	private static final int TW_ICON_SIZE = 13;
	@NonNls
	static final String TOOL_WINDOW_ID = "Dodona";
	
	private final Project project;
	
	/**
	 * DodonaToolWindowFactory constructor.
	 *
	 * @param project the current project
	 */
	public DodonaToolWindowFactory(final Project project) {
		this.project = project;
	}
	
	/**
	 * Creates the ToolWindow.
	 *
	 * @param toolWindow the parent tool window to create the contents in
	 */
	void createToolWindowContent(@NotNull final ToolWindow toolWindow) {
		// Get a request executor.
		final DodonaExecutorHolder executor =
			DodonaAuthenticator.getInstance().getExecutor();
		
		// Set the title and auto close property.
		toolWindow.setTitle(DodonaBundle.NAME);
		toolWindow.setToHideOnEmptyContent(true);
		
		// Create the deadlines tab.
		final DeadlinesTab deadlines = new DeadlinesTab(this.project, executor);
		
		// Create the submissions tab.
		final SubmissionsTab submissions = new SubmissionsTab(
			this.project,
			executor
		);
		
		// Set the ToolWindow.
		toolWindow.setIcon(IconUtil.toSize(Icons.DODONA, TW_ICON_SIZE, TW_ICON_SIZE));
		toolWindow.setTitle(DodonaBundle.message("toolwindow.title"));
		
		// Append all tabs to the content.
		deadlines.setup(toolWindow);
		submissions.setup(toolWindow);
	}
	
	/**
	 * Validates whether this tool window should be shown.
	 *
	 * @return true if the tool window should be shown
	 */
	boolean validate() {
		return Optional.of(this.project)
			.map(DodonaProjectSettings::getInstance)
			.flatMap(DodonaProjectSettings::getCourseId)
			.isPresent();
	}
}
