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
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.util.messages.MessageBusConnection;
import io.github.thepieterdc.dodona.plugin.settings.listeners.ProjectCourseListener;

/**
 * Displays or hides the tool window.
 */
public class ToolWindowSynchronizer implements DumbAware {
	private final DodonaToolWindowFactory factory;
	private final Project project;
	
	/**
	 * ToolWindowSynchronizer constructor.
	 *
	 * @param project the current active project
	 */
	public ToolWindowSynchronizer(final Project project) {
		this.factory = project.getComponent(DodonaToolWindowFactory.class);
		this.project = project;
		
		final MessageBusConnection conn = this.project.getMessageBus().connect();
		
		// Listen for changes to project settings.
		conn.subscribe(ProjectCourseListener.CHANGED_TOPIC, id -> this.update());
	}
	
	/**
	 * Hides the tool window.
	 *
	 * @param mgr the tool window manager
	 */
	private static void hide(final ToolWindowManager mgr) {
		mgr.unregisterToolWindow(DodonaToolWindowFactory.TOOL_WINDOW_ID);
	}
	
	/**
	 * Shows the tool window.
	 *
	 * @param mgr the tool window manager
	 */
	private void show(final ToolWindowManager mgr) {
		final ToolWindow toolWindow = mgr.registerToolWindow(
			DodonaToolWindowFactory.TOOL_WINDOW_ID,
			true,
			ToolWindowAnchor.RIGHT,
			this.project,
			false,
			true
		);
		
		this.factory.createToolWindowContent(toolWindow);
	}
	
	/**
	 * Updates the state of the ToolWindow visibility.
	 */
	private void update() {
		ApplicationManager.getApplication().invokeLater((DumbAwareRunnable) () -> {
			final ToolWindowManager mgr = ToolWindowManager.getInstance(this.project);
			final ToolWindow window = mgr
				.getToolWindow(DodonaToolWindowFactory.TOOL_WINDOW_ID);
			
			if (this.factory.validate() && window == null) {
				this.show(mgr);
			} else if (!this.factory.validate() && window != null) {
				hide(mgr);
			}
		});
	}
}
