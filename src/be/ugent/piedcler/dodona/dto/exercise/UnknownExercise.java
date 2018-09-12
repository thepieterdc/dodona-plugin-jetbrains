/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.exercise;

import be.ugent.piedcler.dodona.dto.course.Course;

/**
 * An exercise of which the name is not known, since it was defined in code and
 * not fetched from Dodona.
 */
public final class UnknownExercise implements Exercise {
	private final Course course;
	private final long id;
	
	/**
	 * UnknownExercise constructor.
	 *
	 * @param id the id of the exercise
	 * @param course the course
	 */
	public UnknownExercise(final long id, final Course course) {
		this.course = course;
		this.id = id;
	}
	
	@Override
	public Course getCourse() {
		return this.course;
	}
	
	@Override
	public String getDisplayName() {
		return String.format("Exercise %d", this.id);
	}
	
	@Override
	public long getId() {
		return this.id;
	}
}
