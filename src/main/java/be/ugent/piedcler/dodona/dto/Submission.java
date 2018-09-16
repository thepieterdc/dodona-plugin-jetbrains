/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

import be.ugent.piedcler.dodona.dto.submission.SubmissionStatus;
import be.ugent.piedcler.dodona.settings.SettingsHelper;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * An exercise for Dodona.
 */
public interface Submission extends Resource {
	@NonNls
	String ENDPOINT = "/submissions";

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
		return getUrl(this.getId());
	}

	/**
	 * Gets the url to the submission
	 *
	 * @param id The submission id
	 * @return the url
	 */
	static String getUrl(long id) {
		return SettingsHelper.getDodonaURL(Submission.ENDPOINT_ID, id);
	}
}
