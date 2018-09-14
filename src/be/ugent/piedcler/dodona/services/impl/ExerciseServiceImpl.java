/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.ExerciseResponse;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.services.ExerciseService;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation class for ExerciseService.
 */
public class ExerciseServiceImpl implements ExerciseService {
	private final Map<Long, Exercise> cache;
	
	/**
	 * ExerciseServiceImpl constructor.
	 */
	public ExerciseServiceImpl() {
		this.cache = new HashMap<>(30);
	}
	
	@Override
	public Exercise get(final long id) {
		final Exercise fromCache = this.cache.get(id);
		System.out.println(fromCache);
		
		if(fromCache == null) {
			System.out.println("api");
			final Exercise fromApi = getFromApi(id);
			System.out.println(fromApi);
			this.cache.put(id, fromApi);
			return fromApi;
		} else {
			System.out.println("cache");
			return fromCache;
		}
	}
	
	/**
	 * Gets an exercise from the api.
	 *
	 * @param id the id of the exercise to fetch
	 * @return the exercise
	 */
	private static Exercise getFromApi(final long id) {
		final String url = String.format(Exercise.ENDPOINT_ID, id);
		return Http.get(url, ExerciseResponse.class).toExercise();
	}
}
