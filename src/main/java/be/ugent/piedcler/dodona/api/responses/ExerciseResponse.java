/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.exercise.ExerciseImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The response from fetching an exercise.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseResponse {
	private static final Pattern JSON_EXTENSION = Pattern.compile(".json", Pattern.LITERAL);
	
	@Nullable
	private final String boilerplate;
	
	private final boolean hasCorrectSolution;
	private final long id;
	private final boolean lastSolutionCorrect;
	private final String name;
	private final String url;
	
	/**
	 * ExerciseResponse constructor.
	 *
	 * @param boilerplate         the boilerplate code of the exercise
	 * @param hasCorrectSolution  indication if there ever was a correct solution submitted
	 * @param id                  the id of the exercise
	 * @param lastSolutionCorrect indication if the last submission was correct
	 * @param name                the name of the exercise
	 * @param url                 the url to the exercise
	 */
	public ExerciseResponse(@Nullable @JsonProperty("boilerplate") final String boilerplate,
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
	
	/**
	 * Gets the boilerplate code.
	 *
	 * @return the boilerplate code
	 */
	public Optional<String> getBoilerplate() {
		return Optional.ofNullable(this.boilerplate);
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * Converts the exercise response to an exercise.
	 *
	 * @return the exercise
	 */
	public Exercise toExercise() {
		return new ExerciseImpl(
			this.id, this.name, this.boilerplate, this.lastSolutionCorrect, this.hasCorrectSolution,
			JSON_EXTENSION.matcher(this.url).replaceAll(Matcher.quoteReplacement(""))
		);
	}
}
