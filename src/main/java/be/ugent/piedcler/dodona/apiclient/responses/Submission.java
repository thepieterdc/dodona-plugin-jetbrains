/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response of getting a Submission from Dodona.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Submission {


	private final boolean accepted;
	private final String url;
	private final String exercise;
	private final long id;
	private final SubmissionStatus status;

	/**
	 * Submission constructor.
	 *  @param accepted the acceptance status
	 * @param url      the url to the submission
	 * @param exercise url to the exercise
	 * @param id       the id of the submission
	 * @param status   the status
	 */
	public Submission(@JsonProperty("accepted") final boolean accepted,
					  @JsonProperty("url") String url,
					  @JsonProperty("exercise") final String exercise,
					  @JsonProperty("id") final long id,
					  @JsonProperty("status") final SubmissionStatus status) {
		this.accepted = accepted;
		this.url = url;
		this.exercise = exercise;
		this.id = id;
		this.status = status;
	}

	public boolean getAccepted() {
		return accepted;
	}

	public String getExercise() {
		return exercise;
	}

	public long getId() {
		return id;
	}

	public SubmissionStatus getStatus() {
		return status;
	}

	public String getUrl() {
		return url;
	}
}
