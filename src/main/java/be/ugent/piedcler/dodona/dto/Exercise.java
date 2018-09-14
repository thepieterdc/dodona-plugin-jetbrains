/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

import be.ugent.piedcler.dodona.Configuration;
import org.jetbrains.annotations.NonNls;

/**
 * An exercise for Dodona.
 */
public interface Exercise extends Resource {
	@NonNls
	String ENDPOINT_ID = Configuration.DODONA_URL + "/exercises/%d";
	
	/**
	 * Gets the name of the exercise.
	 *
	 * @return the name
	 */
	String getName();
	
	@Override
	default String getUrl() {
		return String.format(Exercise.ENDPOINT_ID, this.getId());
	}
}
