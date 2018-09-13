/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Submission;
import org.jetbrains.annotations.NonNls;

/**
 * Utilities to submit exercises to Dodona.
 */
public enum Exercises {
	;
	
	@NonNls
	private static final String ENDPOINT_SUBMIT = Configuration.DODONA_URL + "/submissions";
	
	/**
	 * Submits an exercise to Dodona (synchronously).
	 *
	 * @param exercise the exercise to submit
	 * @param code     the solution to submit
	 * @return the submission (pending)
	 */
	public static Submission submit(final Exercise exercise, final String code) {
		System.out.println("test");
		return null;
	}
//	public static Submission submit(final Exercise exercise, final String code) {
//		final Map<String, Object> body = new HashMap<>(3);
//
//		try {
//			body.put("submission[code]", URLEncoder.encode(code, "UTF-8"));
//		} catch (final UnsupportedEncodingException ex) {
//			throw new RuntimeException(ex);
//		}
//
//		body.put("submission[course_id]", exercise.getCourse().getId());
//		body.put("submission[exercise_id]", exercise.getId());
//
//		final SubmitResponse response = Http.post(Exercises.ENDPOINT_SUBMIT, body, SubmitResponse.class);
//
//		final Submission submission = new PendingSubmission(response.getId(), exercise);
//		if (response.getStatus().equals(SubmitResponse.STATUS_OK)) {
//			return submission;
//		} else {
//			throw new SubmissionException(submission);
//		}
//	}
}
