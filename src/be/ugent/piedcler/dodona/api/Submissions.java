/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.api.responses.SubmissionResponse;
import be.ugent.piedcler.dodona.dto.submission.AcceptedSubmission;
import be.ugent.piedcler.dodona.dto.submission.RejectedSubmission;
import be.ugent.piedcler.dodona.dto.submission.Submission;

/**
 * Utilities to submit exercises to Dodona.
 */
public enum Submissions {
	;
	
	/**
	 * Fetches a submission from Dodona (synchronously).
	 *
	 * @param submission to submission to refresh
	 * @return the updated submission
	 */
	public static Submission get(final Submission submission) {
		final SubmissionResponse response = Http.get(submission.getUrl(), SubmissionResponse.class);
		
		if (response.isAccepted()) {
			return new AcceptedSubmission(submission.getId(), submission.getExercise());
		}
		
		if (response.getStatus().equals(SubmissionResponse.STATUS_PENDING)) {
			return submission;
		}
		
		return new RejectedSubmission(submission.getId(), submission.getExercise());
	}
}
