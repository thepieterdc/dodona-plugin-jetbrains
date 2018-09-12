/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.api.responses.SubmitResponse;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import be.ugent.piedcler.dodona.dto.submission.PendingSubmission;
import be.ugent.piedcler.dodona.dto.submission.Submission;
import be.ugent.piedcler.dodona.exceptions.errors.SubmissionException;
import org.jetbrains.annotations.NonNls;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities to submit exercises to Dodona.
 */
public enum SubmitExercise {
	;
	
	@NonNls
	private static final String ENDPOINT_SUBMIT = "/submissions.json";
	
	/**
	 * Submits an exercise to Dodona.
	 *
	 * @param exercise the exercise to submit
	 * @param code     the solution to submit
	 */
	//TODO provide feedback. Issue #2.
	public static Submission submit(final Exercise exercise, final String code) {
		final Map<String, Object> body = new HashMap<>(3);
		
		try {
			body.put("submission[code]", URLEncoder.encode(code, "UTF-8"));
		} catch (final UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		
		body.put("submission[course_id]", exercise.getCourse().getId());
		body.put("submission[exercise_id]", exercise.getId());
		
		final SubmitResponse response = Http.post(SubmitExercise.ENDPOINT_SUBMIT, body, SubmitResponse.class);
		
		final Submission submission = new PendingSubmission(response.getId(), exercise);
		if (response.getStatus().equals(SubmitResponse.STATUS_OK)) {
			return submission;
		} else {
			throw new SubmissionException(submission);
		}
	}
}
