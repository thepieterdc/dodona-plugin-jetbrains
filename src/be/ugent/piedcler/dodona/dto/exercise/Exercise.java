/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.exercise;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.dto.Resource;
import be.ugent.piedcler.dodona.dto.course.Course;
import org.jetbrains.annotations.NonNls;

/**
 * An exercise for Dodona.
 */
public interface Exercise extends Resource {
	@NonNls
	String ENDPOINT_ID = Configuration.DODONA_URL + "/exercises/%d";
	
	/**
	 * Gets the course this exercise belongs to.
	 *
	 * @return the course
	 */
	Course getCourse();
	
	@Override
	String toString();
	
	@Override
	default String getUrl() {
		return String.format(Exercise.ENDPOINT_ID, this.getId());
	}
}
