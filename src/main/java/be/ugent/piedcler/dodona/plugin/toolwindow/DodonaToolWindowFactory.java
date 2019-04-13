/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates tool windows.
 */
public class DodonaToolWindowFactory implements ToolWindowFactory, DumbAware {
	@Override
	public void createToolWindowContent(@NotNull final Project project,
	                                    @NotNull final ToolWindow toolWindow) {
		final DodonaToolWindowView toolWindowView = DodonaToolWindowView.getInstance(project);
		toolWindowView.initToolWindow(toolWindow);
	}
}
