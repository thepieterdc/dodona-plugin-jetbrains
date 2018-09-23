/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The response from fetching a series.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Series {

	private final long id;
	private final String name;
	private final String url;
	private final String exercises;

	/**
	 * Series constructor.
	 *
	 * @param id        the id of the series
	 * @param name      the name of the series
	 * @param url       the url of the series
	 * @param exercises the url to the list of exercises
	 */
	public Series(@JsonProperty("id") final long id,
				  @JsonProperty("name") final String name,
				  @JsonProperty("url") final String url,
				  @JsonProperty("exercises") final String exercises) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.exercises = exercises;
	}


	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getExercises() {
		return exercises;
	}
}
