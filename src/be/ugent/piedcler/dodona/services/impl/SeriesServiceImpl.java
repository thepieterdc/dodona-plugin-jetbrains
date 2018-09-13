/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.SeriesResponse;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.services.SeriesService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
				.orElseGet(() -> this.cache.put(id, SeriesServiceImpl.getFromApi(id)));
	}
	
	/**
	 * Gets a series from the api.
	 *
	 * @param id the id of the series to fetch
	 * @return the series
	 */
	private static Series getFromApi(final long id) {
		final String url = String.format(Series.ENDPOINT_ID, id);
		return Http.get(url, SeriesResponse.class).toSeries();
	}
}
