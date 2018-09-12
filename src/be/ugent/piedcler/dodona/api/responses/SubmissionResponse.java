/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NonNls;

/**
 * The response of getting a Submission from Dodona.
 */
public class SubmissionResponse {
	@NonNls
	public static final String STATUS_PENDING = "running";
	
	private final boolean accepted;
	private final long id;
	private final String status;
	
	/**
	 * SubmissionResponse constructor.
	 *
	 * @param accepted the acceptance status
	 * @param id       the id of the submission
	 * @param status   the status
	 */
	public SubmissionResponse(@JsonProperty("accepted") final boolean accepted,
	                          @JsonProperty("id") final long id,
	                          @JsonProperty("status") final String status) {
		this.accepted = accepted;
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
	
	/**
	 * Gets the acceptance status.
	 *
	 * @return the acceptance status
	 */
	public boolean isAccepted() {
		return this.accepted;
	}
}
