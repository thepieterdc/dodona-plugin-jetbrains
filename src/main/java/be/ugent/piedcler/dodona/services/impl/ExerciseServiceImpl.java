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
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
		return Optional.ofNullable(this.cache.get(id))
			.orElseGet(() -> {
				final Exercise ret = ExerciseServiceImpl.getFromApi(id);
				this.cache.put(id, ret);
				return ret;
			});
	}
	
	@Override
	@NotNull
	public Exercise getByUrl(final String url) {
		return Exercise.getId(url).map(this::get).orElseGet(() -> {
			final Exercise ret = ExerciseServiceImpl.getFromApi(url);
			this.cache.put(ret.getId(), ret);
			return ret;
		});
	}
	
	/**
	 * Gets an exercise from the api.
	 *
	 * @param id the id of the exercise to fetch
	 * @return the exercise
	 */
	private static Exercise getFromApi(final long id) {
		final String url = Exercise.getUrl(id);
		return Http.get(url, ExerciseResponse.class).toExercise();
	}
	
	/**
	 * Gets an exercise from the api.
	 *
	 * @param url the url of the exercise to fetch
	 * @return the exercise
	 */
	private static Exercise getFromApi(final String url) {
		return Http.get(url, ExerciseResponse.class).toExercise();
	}
}
