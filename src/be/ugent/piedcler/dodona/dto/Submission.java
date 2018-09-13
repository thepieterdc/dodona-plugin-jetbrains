/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.dto.submission.SubmissionStatus;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * An exercise for Dodona.
 */
public interface Submission extends Resource {
	@NonNls
	String ENDPOINT = Configuration.DODONA_URL + "/submissions";
	
	@NonNls
	String ENDPOINT_ID = Submission.ENDPOINT + "/%d";
	
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
	@NotNull
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
