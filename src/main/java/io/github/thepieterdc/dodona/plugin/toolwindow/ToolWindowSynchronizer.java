/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowEP;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import io.github.thepieterdc.dodona.plugin.settings.listeners.ProjectCourseListener;

/**
 * Displays or hides the tool window.
 */
public class ToolWindowSynchronizer {
	private final Project project;
	
	/**
	 * ToolWindowSynchronizer constructor.
	 *
	 * @param project the current active project
	 */
	public ToolWindowSynchronizer(final Project project) {
		this.project = project;
		this.project.getMessageBus().connect().subscribe(
			ProjectCourseListener.CHANGED_TOPIC,
			id -> this.update()
		);
	}
	
	/**
	 * Updates the state of the ToolWindow visibility.
	 */
	private void update() {
		ApplicationManager.getApplication().invokeLater((DumbAwareRunnable) () -> {
			for (final ToolWindowEP ep : ToolWindowEP.EP_NAME.getExtensions()) {
				if (DodonaToolWindowFactory.class.isAssignableFrom(ep.getFactoryClass())) {
					final ToolWindowFactory factory = ep.getToolWindowFactory();
					final ToolWindowManager mgr = ToolWindowManager.getInstance(this.project);
					if (mgr.getToolWindow(DodonaToolWindowFactory.TOOL_WINDOW_ID) == null) {
						final ToolWindow toolWindow = mgr.registerToolWindow(
							DodonaToolWindowFactory.TOOL_WINDOW_ID,
							true,
							ToolWindowAnchor.RIGHT,
							this.project,
							false,
							true
						);
						factory.createToolWindowContent(this.project, toolWindow);
					}
				}
			}
		});
	}
}
