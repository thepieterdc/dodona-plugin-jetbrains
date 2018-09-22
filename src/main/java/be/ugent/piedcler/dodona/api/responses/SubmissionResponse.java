/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Submission;
import be.ugent.piedcler.dodona.dto.submission.CorrectSubmission;
import be.ugent.piedcler.dodona.dto.submission.PendingSubmission;
import be.ugent.piedcler.dodona.dto.submission.WrongSubmission;
import be.ugent.piedcler.dodona.services.ExerciseService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NonNls;

/**
 * The response of getting a Submission from Dodona.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmissionResponse {
	@NonNls
	public static final String STATUS_PENDING = "running";
	
	private final boolean accepted;
	private final String exercise;
	private final long id;
	private final String status;
	
	/**
	 * SubmissionResponse constructor.
	 *
	 * @param accepted the acceptance status
	 * @param exercise url to the exercise
	 * @param id       the id of the submission
	 * @param status   the status
	 */
	public SubmissionResponse(@JsonProperty("accepted") final boolean accepted,
	                          @JsonProperty("exercise") final String exercise,
	                          @JsonProperty("id") final long id,
	                          @JsonProperty("status") final String status) {
		this.accepted = accepted;
		this.exercise = exercise;
		this.id = id;
		this.status = status;
	}
	
	/**
	 * Converts the submission response to a submission.
	 *
	 * @return the submission
	 */
	public Submission toSubmission() {
		final Exercise convertedExercise = ExerciseService.getInstance().getByUrl(this.exercise);
		
		if (this.accepted) {
			return new CorrectSubmission(this.id, convertedExercise);
		} else if (SubmissionResponse.STATUS_PENDING.equals(this.status)) {
			return new PendingSubmission(this.id, convertedExercise);
		}
		return new WrongSubmission(this.id, convertedExercise);
	}
}
