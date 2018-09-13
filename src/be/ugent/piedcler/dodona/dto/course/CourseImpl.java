/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.course;

import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Series;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Implementation class of Course.
 */
public final class CourseImpl implements Course {
	private final long id;
	private final String name;
	private final Collection<Series> series;
	private final String teacher;
	private final String year;
	
	/**
	 * CourseImpl constructor.
	 *
	 * @param id      the id of the course
	 * @param name    the name of the course
	 * @param teacher the teacher of the course
	 * @param year    the academic year
	 */
	public CourseImpl(final long id, final String name, final String teacher, final String year) {
		this.id = id;
		this.name = name;
		this.series = new HashSet<>(10);
		this.teacher = teacher;
		this.year = year;
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
	public Collection<Series> getSeries() {
		return Collections.unmodifiableCollection(this.series);
	}
	
	@Override
	public String getTeacher() {
		return this.teacher;
	}
	
	@Override
	public String getYear() {
		return this.year;
	}
	
	/**
	 * Sets the exercise series of this course.
	 *
	 * @param series the exercise series
	 * @return fluent setter
	 */
	@NotNull
	public Course setSeries(final Collection<Series> series) {
		this.series.clear();
		this.series.addAll(series);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("Course{id=%d}", this.id);
	}
}
