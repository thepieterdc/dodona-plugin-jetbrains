/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.tabs;

import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
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
	 * @param executor request executor
	 * @param courseId the id of the current active course
	 */
	public DeadlinesTab(final DodonaExecutor executor, final long courseId) {
		super(TAB_TITLE);
		this.deadlinesPanel = new DeadlinesPanel(courseId, getFutureDeadlines(executor));
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
	private static CompletableFuture<List<Deadline>> getFutureDeadlines(final DodonaExecutor executor) {
		return executor.execute(DodonaClient::root)
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
