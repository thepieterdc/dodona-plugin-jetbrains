/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.ExerciseResponse;
import be.ugent.piedcler.dodona.api.responses.SeriesResponse;
import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.services.SeriesService;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation class for SeriesService.
 */
public class SeriesServiceImpl implements SeriesService {
	private final Map<Long, Series> cache;
	
	/**
	 * SeriesServiceImpl constructor.
	 */
	public SeriesServiceImpl() {
		this.cache = new HashMap<>(5);
	}
	
	@Override
	public Series get(final long id) {
		return Optional.ofNullable(this.cache.get(id))
			.filter(series -> !series.getExercises().isEmpty())
			.orElseGet(() -> {
				final Series ret = SeriesServiceImpl.getFromApi(id);
				this.cache.put(id, ret);
				return ret;
			});
	}
	
	/**
	 * Gets a series from the api.
	 *
	 * @param id the id of the series to fetch
	 * @return the series
	 */
	private static Series getFromApi(final long id) {
		final String url = Series.getUrl(id);
		final Series series = Http.get(url, SeriesResponse.class).toSeries();
		return series.setExercises(getExercisesFromApi(id));
	}
	
	/**
	 * Gets the exercises from a given series.
	 *
	 * @param series the series id
	 * @return the exercises in the series
	 */
	@NotNull
	private static List<Exercise> getExercisesFromApi(final long series) {
		return Stream.of(Http.get(Series.getExercisesUrl(series), ExerciseResponse[].class))
			.map(ExerciseResponse::toExercise)
			.collect(Collectors.toList());
	}
}
