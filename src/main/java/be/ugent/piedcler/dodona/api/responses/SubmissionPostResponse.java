/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NonNls;

/**
 * The response from a submitting a solution to Dodona.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmissionPostResponse {
	@NonNls
	public static final String STATUS_OK = "ok";
	
	private final long id;
	private final String status;
	
	/**
	 * SubmitResponse constructor.
	 *
	 * @param id     the id of the submission
	 * @param status the status
	 */
	public SubmissionPostResponse(@JsonProperty("id") final long id,
	                              @JsonProperty("status") final String status) {
		this.id = id;
		this.status = status;
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
}
