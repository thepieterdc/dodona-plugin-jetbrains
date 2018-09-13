/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

/**
 * A solution to submit to Dodona.
 */
public class Solution {
	private final String code;
	private final Course course;
	private final Exercise exercise;
	
	/**
	 * Solution constructor.
	 *
	 * @param code     the code to submit
	 * @param course   the course
	 * @param exercise the exercise
	 */
	public Solution(final Course course, final Exercise exercise, final String code) {
		this.code = code;
		this.course = course;
		this.exercise = exercise;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Gets the course.
	 *
	 * @return the course
	 */
	public Course getCourse() {
		return this.course;
	}
	
	/**
	 * Gets the exercise.
	 *
	 * @return the exercise
	 */
	public Exercise getExercise() {
		return this.exercise;
	}
}
