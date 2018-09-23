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
import org.jetbrains.annotations.Nullable;

/**
 * The response from fetching an exercise.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Exercise {

	@Nullable
	private final String boilerplate;
	
	private final boolean hasCorrectSolution;
	private final long id;
	private final boolean lastSolutionCorrect;
	private final String name;
	private final String url;
	
	/**
	 * Exercise constructor.
	 *
	 * @param boilerplate         the boilerplate code of the exercise
	 * @param hasCorrectSolution  indication if there ever was a correct solution submitted
	 * @param id                  the id of the exercise
	 * @param lastSolutionCorrect indication if the last submission was correct
	 * @param name                the name of the exercise
	 * @param url                 the url to the exercise
	 */
	public Exercise(@Nullable @JsonProperty("boilerplate") final String boilerplate,
					@JsonProperty("has_correct_solution") final boolean hasCorrectSolution,
					@JsonProperty("id") final long id,
					@JsonProperty("last_solution_correct") final boolean lastSolutionCorrect,
					@JsonProperty("name") final String name,
					@JsonProperty("url") final String url) {
		this.boilerplate = boilerplate;
		this.hasCorrectSolution = hasCorrectSolution;
		this.id = id;
		this.lastSolutionCorrect = lastSolutionCorrect;
		this.name = name;
		this.url = url;
	}


	@Nullable
	public String getBoilerplate() {
		return boilerplate;
	}

	public boolean getHasCorrectSolution() {
		return hasCorrectSolution;
	}

	public long getId() {
		return id;
	}

	public boolean getLastSolutionCorrect() {
		return lastSolutionCorrect;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
}
