/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.course;

/**
 * A course of which the name is not known, since it was defined in code and
 * not fetched from Dodona.
 */
public final class UnknownCourse implements Course {
	private final long id;
	
	/**
	 * UnknownCourse constructor.
	 *
	 * @param id     the id of the course
	 */
	public UnknownCourse(final long id) {
		this.id = id;
	}
	
	@Override
	public String getDisplayName() {
		return String.format("Course %d", this.id);
	}
	
	@Override
	public long getId() {
		return this.id;
	}
}
