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
 * The response from a submitting a solution to Dodona.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class SubmissionPost {

	private final long id;
	private final String status;
	private final String url;

	/**
	 * SubmitResponse constructor.
	 *
	 * @param id     the id of the submission
	 * @param status the status
	 * @param url    the url
	 */
	public SubmissionPost(@JsonProperty("id") final long id,
						  @JsonProperty("status") final String status,
						  @JsonProperty("url") final String url) {
		this.id = id;
		this.status = status;
		this.url = url;
	}

	/**
	 * Gets the id of the submission.
	 *
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	public String getUrl() {
		return url;
	}
}
