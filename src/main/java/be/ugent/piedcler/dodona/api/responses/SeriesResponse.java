/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.dto.series.SeriesImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The response from fetching a series.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesResponse {
	private static final Pattern JSON_EXTENSION = Pattern.compile(".json", Pattern.LITERAL);
	
	private final long id;
	private final String name;
	private final String url;

	/**
	 * SeriesResponse constructor.
	 *
	 * @param id   the id of the series
	 * @param name the name of the series
	 * @param url  the url of the series
	 */
	public SeriesResponse(@JsonProperty("id") final long id,
						  @JsonProperty("name") final String name,
						  @JsonProperty("url") final String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	/**
	 * Converts the series response to a series.
	 *
	 * @return the series
	 */
	public Series toSeries() {
		return new SeriesImpl(
			this.id, this.name, JSON_EXTENSION.matcher(this.url).replaceAll(Matcher.quoteReplacement(""))
		);
	}
}
