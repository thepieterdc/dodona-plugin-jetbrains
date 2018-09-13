/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.submission;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.dto.Resource;
import be.ugent.piedcler.dodona.dto.Exercise;
import org.jetbrains.annotations.NonNls;

/**
 * An exercise for Dodona.
 */
public interface Submission extends Resource {
	@NonNls
	String ENDPOINT_ID = Configuration.DODONA_URL + "/submissions/%d";
	
	/**
	 * Gets the exercise this submission belongs to.
	 *
	 * @return the exercise
	 */
	Exercise getExercise();
	
	/**
	 * Gets the status of the submission.
	 *
	 * @return the status
	 */
	SubmissionStatus getStatus();
	
	/**
	 * Gets the url to this submission.
	 *
	 * @return the url
	 */
	default String getUrl() {
		return String.format(Submission.ENDPOINT_ID, this.getId());
	}
}
