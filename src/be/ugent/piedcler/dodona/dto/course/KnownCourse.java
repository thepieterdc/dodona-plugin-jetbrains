/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A course of which all details are known since it was fetched from Dodona.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class KnownCourse implements Course {
	private final long id;
	private final String name;
	private final String teacher;
	private final String year;
	
	/**
	 * KnownCourse constructor.
	 *
	 * @param id      the id of the course
	 * @param name    the name of the course
	 * @param teacher the teacher of the course
	 * @param year    the academic year
	 */
	public KnownCourse(@JsonProperty("id") final long id,
	                   @JsonProperty("name") final String name,
	                   @JsonProperty("teacher") final String teacher,
	                   @JsonProperty("year") final String year) {
		this.id = id;
		this.name = name;
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
	public String getTeacher() {
		return this.teacher;
	}
	
	@Override
	public String getYear() {
		return this.year;
	}
	
	@Override
	public String toString() {
		return String.format("Course{id=%d}", this.id);
	}
}
