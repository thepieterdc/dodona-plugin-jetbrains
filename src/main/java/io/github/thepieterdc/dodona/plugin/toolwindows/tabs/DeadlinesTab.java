/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindows.tabs;

import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.toolwindows.ui.deadlines.DeadlinesPanel;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.responses.RootResponse;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for the tab showing upcoming deadlines
 */
public class DeadlinesTab extends AbstractTab {
	private static final String TAB_TITLE = DodonaBundle.message("toolwindow.deadlines.title");
	
	private final DodonaExecutor executor;
	
	private final DeadlinesPanel deadlinesPanel;
	
	/**
	 * DTO class representing a deadline.
	 */
	public static class Deadline implements Comparable<Deadline> {
		public final long courseId;
		public final String courseName;
		public final ZonedDateTime deadline;
		public final String seriesName;
		
		/**
		 * Deadline constructor.
		 *
		 * @param courseId   the id of the course
		 * @param courseName the name of the course
		 * @param seriesName the name of the series
		 * @param deadline   the deadline
		 */
		public Deadline(final long courseId,
		                final String courseName,
		                final String seriesName,
		                final ZonedDateTime deadline) {
			this.courseId = courseId;
			this.courseName = courseName;
			this.deadline = deadline;
			this.seriesName = seriesName;
		}
		
		@Override
		public int compareTo(@NotNull final Deadline o) {
			final int compareDeadline = this.deadline.compareTo(o.deadline);
			if (compareDeadline == 0) {
				return this.courseName.compareTo(o.courseName);
			}
			return compareDeadline;
		}
	}
	
	/**
	 * DeadlinesTab constructor.
	 *
	 * @param courseId the id of the current active course
	 */
	public DeadlinesTab(final DodonaExecutor executor, final long courseId) {
		super(DeadlinesTab.TAB_TITLE);
		this.executor = executor;
		this.deadlinesPanel = new DeadlinesPanel(courseId);
		this.update();
	}
	
	@Nonnull
	@Override
	JComponent createContent() {
		return this.deadlinesPanel;
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
	
	/**
	 * Updates the list of deadlines.
	 */
	private void update() {
		this.deadlinesPanel.showLoading();
		this.executor.execute(DodonaClient::root).whenComplete(
			(root, error) -> SwingUtilities.invokeLater(() -> {
				this.deadlinesPanel.update(DeadlinesTab.parseDeadlines(root));
				this.deadlinesPanel.showDeadlines();
			})
		);
	}
}
