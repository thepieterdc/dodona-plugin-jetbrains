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
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.settings.listeners.ProjectCourseListener;
import io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines.DeadlinesPanel;
import io.github.thepieterdc.dodona.plugin.ui.Deadline;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.responses.RootResponse;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
		this.deadlinesPanel = new DeadlinesPanel(getFutureDeadlines(executor));
		
		// Listen for project changes.
		project.getMessageBus().connect().subscribe(
			ProjectCourseListener.CHANGED_TOPIC,
			this.deadlinesPanel::setCurrentCourse
		);
		
		// Set the current project.
		DodonaProjectSettings.getInstance(project)
			.getCourseId().ifPresent(this.deadlinesPanel::setCurrentCourse);
	}
	
	@Nonnull
	@Override
	JComponent createContent() {
		return this.deadlinesPanel;
	}
	
	/**
	 * Gets the deadlines request.
	 *
	 * @param executor request executor
	 * @return request
	 */
	@Nonnull
	private static CompletableFuture<List<Deadline>> getFutureDeadlines(final DodonaExecutorHolder executor) {
		return executor.getExecutor().execute(DodonaClient::root)
			.thenApply(DeadlinesTab::parseDeadlines);
	}
	
	private static List<Deadline> parseDeadlines(final RootResponse root) {
		// Create a map of the course ids to their names.
		final Map<Long, String> courseNames = root.getUser()
			.getSubscribedCourses()
			.stream()
			.collect(Collectors.toMap(Course::getId, Course::getName));
		
		// Create a deadline object for every deadline.
		return root.getDeadlineSeries().stream().map(series -> new Deadline(
				Course.getId(series.getCourseUrl()).orElse(0L),
				Course.getId(series.getCourseUrl()).map(courseNames::get).orElse(DodonaBundle.message("course.unknown")),
				series.getName(),
				series.getDeadline().orElseThrow(RuntimeException::new)
			)
		)
			.sorted()
			.collect(Collectors.toList());
	}
}
