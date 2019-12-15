/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.tabs;

import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.settings.listeners.ProjectCourseListener;
import io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines.DeadlinesPanel;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Controller for the tab showing upcoming deadlines
 */
public class DeadlinesTab extends AbstractTab {
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
		this.deadlinesPanel = new DeadlinesPanel(executor);
		
		// Listen for project changes.
		project.getMessageBus().connect().subscribe(
			ProjectCourseListener.CHANGED_TOPIC,
			this.deadlinesPanel::setCurrentCourse
		);
		
		// Set the current project.
		DodonaProjectSettings.getInstance(project)
			.getCourseId().ifPresent(this.deadlinesPanel::setCurrentCourse);
		
		// Load the deadlines.
		this.deadlinesPanel.requestUpdate();
	}
	
	@Nonnull
	@Override
	JComponent createContent() {
		return this.deadlinesPanel;
	}
}
