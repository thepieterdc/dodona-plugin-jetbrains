/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import org.jetbrains.annotations.NonNls;

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
	public static void submit(final Exercise exercise, final String code) {
		final Map<String, Object> body = new HashMap<>(3);
		body.put("submission[code]", code);
		body.put("submission[course_id]", exercise.getCourse().getId());
		body.put("submission[exercise_id]", exercise.getId());
		
		try {
			Http.post(SubmitExercise.ENDPOINT_SUBMIT, body);
		} catch(final Exception ex) {
			ex.printStackTrace();
		}
	}
}
