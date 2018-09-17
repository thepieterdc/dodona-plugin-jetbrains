/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.exercise.ExerciseImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * The response from fetching an exercise.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseResponse {
	@Nullable
	private final String boilerplate;
	
	private final long id;
	private final String name;
	
	/**
	 * ExerciseResponse constructor.
	 *
	 * @param boilerplate the boilerplate code of the exercise
	 * @param id          the id of the exercise
	 * @param name        the name of the exercise
	 */
	public ExerciseResponse(@Nullable @JsonProperty("boilerplate") final String boilerplate,
	                        @JsonProperty("id") final long id,
	                        @JsonProperty("name") final String name) {
		this.boilerplate = boilerplate;
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Gets the boilerplate code.
	 *
	 * @return the boilerplate code
	 */
	@Nullable
	public String getBoilerplate() {
		return this.boilerplate;
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
		return new ExerciseImpl(this.id, this.name, this.boilerplate);
	}
}
