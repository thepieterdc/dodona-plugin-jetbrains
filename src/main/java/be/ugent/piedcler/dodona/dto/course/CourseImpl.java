/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.course;

import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Series;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Implementation class of Course.
 */
public final class CourseImpl implements Course {
	private final long id;
	private final String name;
	private final List<Series> series;
	private final String teacher;
	private final String year;
	private final String url;

	/**
	 * CourseImpl constructor.
	 *
	 * @param id      the id of the course
	 * @param name    the name of the course
	 * @param teacher the teacher of the course
	 * @param year    the academic year
	 * @param url     the url to the course
	 */
	public CourseImpl(final long id, final String name, final String teacher, final String year, final String url) {
		this.id = id;
		this.name = name;
		this.series = new ArrayList<>(10);
		this.teacher = teacher;
		this.year = year;
		this.url = url;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<Series> getSeries() {
		return Collections.unmodifiableList(this.series);
	}

	@Override
	public String getTeacher() {
		return this.teacher;
	}

	@Override
	public String getYear() {
		return this.year;
	}

	@Override
	@NotNull
	public Course setSeries(final Collection<Series> nw) {
		this.series.clear();
		this.series.addAll(nw);
		return this;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return String.format("Course{id=%d}", this.id);
	}
}
