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
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.util.IconUtil;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.DeadlinesTab;
import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.SubmissionsTab;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Creates the tool window.
 */
public class DodonaToolWindowFactory implements Condition<Project>, DumbAware,
	ToolWindowFactory {
	
	private static final int TW_ICON_SIZE = 13;
	
	@NonNls
	static final String TOOL_WINDOW_ID = "Dodona";
	
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
		
		// Create the submissions tab.
		final SubmissionsTab submissions = new SubmissionsTab(
			project,
			executor
		);
		
		// Set the ToolWindow.
		toolWindow.setIcon(IconUtil.toSize(Icons.DODONA, TW_ICON_SIZE, TW_ICON_SIZE));
		toolWindow.setTitle(DodonaBundle.message("toolwindow.title"));
		
		// Append all tabs to the content.
		deadlines.setup(toolWindow);
		submissions.setup(toolWindow);
	}
	
	@Override
	public boolean value(@Nullable final Project project) {
		return Optional.ofNullable(project)
			.map(DodonaProjectSettings::getInstance)
			.flatMap(DodonaProjectSettings::getCourseId)
			.isPresent();
	}
}
