/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.api.responses.SeriesResponse;
import be.ugent.piedcler.dodona.api.responses.SubmitResponse;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.dto.submission.PendingSubmission;
import be.ugent.piedcler.dodona.dto.submission.Submission;
import be.ugent.piedcler.dodona.exceptions.errors.SubmissionException;
import org.jetbrains.annotations.NonNls;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities to submit exercises to Dodona.
 */
public enum Exercises {
	;
	
	@NonNls
	private static final String ENDPOINT_SUBMIT = Configuration.DODONA_URL + "/submissions";
	
	/**
	 * Gets all exercises in a given series.
	 *
	 * @param series the series
	 * @return the exercises
	 */
	public static Collection<Exercise> getAll(final Series series) {
		return Http.get(series.getUrl(), SeriesResponse.class).getExercises();
	}
	
	/**
	 * Submits an exercise to Dodona (synchronously).
	 *
	 * @param exercise the exercise to submit
	 * @param code     the solution to submit
	 * @return the submission (pending)
	 */
	public static Submission submit(final Exercise exercise, final String code) {
		final Map<String, Object> body = new HashMap<>(3);
		
		try {
			body.put("submission[code]", URLEncoder.encode(code, "UTF-8"));
		} catch (final UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		
		body.put("submission[course_id]", exercise.getCourse().getId());
		body.put("submission[exercise_id]", exercise.getId());
		
		final SubmitResponse response = Http.post(Exercises.ENDPOINT_SUBMIT, body, SubmitResponse.class);
		
		final Submission submission = new PendingSubmission(response.getId(), exercise);
		if (response.getStatus().equals(SubmitResponse.STATUS_OK)) {
			return submission;
		} else {
			throw new SubmissionException(submission);
		}
	}
}
