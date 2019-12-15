/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Series;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * A deadline.
 */
public class Deadline implements Comparable<Deadline> {
	private final long courseId;
	private final String courseName;
	private final ZonedDateTime deadline;
	private final String seriesName;
	
	/**
	 * Deadline constructor.
	 *
	 * @param courseId   the id of the course
	 * @param courseName the name of the course
	 * @param seriesName the name of the series
	 * @param deadline   the deadline
	 */
	private Deadline(final long courseId,
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
	
	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Deadline
			&& this.courseId == ((Deadline) obj).courseId
			&& this.seriesName.equals(((Deadline) obj).seriesName)
			&& this.deadline == ((Deadline) obj).deadline;
	}
	
	/**
	 * Gets the course id.
	 *
	 * @return the course id
	 */
	public long getCourseId() {
		return this.courseId;
	}
	
	/**
	 * Gets the course name.
	 *
	 * @return the course name
	 */
	@Nonnull
	public String getCourseName() {
		return this.courseName;
	}
	
	/**
	 * Gets the deadline.
	 *
	 * @return the deadline
	 */
	@Nonnull
	public ZonedDateTime getDeadline() {
		return this.deadline;
	}
	
	/**
	 * Gets the series name.
	 *
	 * @return the series name
	 */
	@Nonnull
	public String getSeriesName() {
		return this.seriesName;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.courseName, this.seriesName, this.deadline);
	}
	
	/**
	 * Parses a series to a deadline object.
	 *
	 * @param courses map from course ids to course names
	 * @param series  the series to convert
	 */
	@Nonnull
	public static Deadline parse(final Map<Long, String> courses,
	                             final Series series) {
		return new Deadline(
			Course.getId(series.getCourseUrl()).orElse(0L),
			Course.getId(series.getCourseUrl()).map(courses::get)
				.orElse(DodonaBundle.message("course.unknown")),
			series.getName(),
			series.getDeadline().orElseThrow(RuntimeException::new)
		);
	}
}
