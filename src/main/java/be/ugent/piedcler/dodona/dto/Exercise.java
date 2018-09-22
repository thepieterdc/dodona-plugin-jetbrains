/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

import be.ugent.piedcler.dodona.settings.SettingsHelper;
import org.jetbrains.annotations.NonNls;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * An exercise for Dodona.
 */
public interface Exercise extends Resource {
	@NonNls
	String ENDPOINT_ID = "/exercises/%d";

	Pattern URL_ID_PATTERN = Pattern.compile("exercises/(\\d+)");

	/**
	 * Gets the boilerplate code.
	 *
	 * @return the boilerplate code
	 */
	Optional<String> getBoilerplate();

	/**
	 * Gets the id of the exercise from the url.
	 *
	 * @param url the url
	 * @return the id, if found
	 */
	static Optional<Long> getId(final String url) {
		return Optional.of(URL_ID_PATTERN.matcher(url))
			.filter(matcher -> matcher.find() && (matcher.groupCount() == 2))
			.map(matcher -> matcher.group(1))
			.map(Long::parseLong);
	}

	/**
	 * Gets the name of the exercise.
	 *
	 * @return the name
	 */
	String getName();

	boolean getLastSolutionCorrect();

	boolean getHasCorrectSolution();

	@Override
	default String getUrl() {
		return getUrl(this.getId());
	}

	/**
	 * Gets the url to an exercise.
	 *
	 * @param id the id of the exercise
	 * @return the url
	 */
	static String getUrl(final long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_ID, id);
	}
}
