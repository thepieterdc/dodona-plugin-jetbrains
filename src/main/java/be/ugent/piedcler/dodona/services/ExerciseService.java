/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Exercise;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;

/**
 * Service for Exercise entities.
 */
public interface ExerciseService extends ResourceService<Exercise> {
	/**
	 * Gets an exercise from the given url.
	 *
	 * @param url the url to query
	 * @return the exercise found
	 */
	@NotNull
	Exercise getByUrl(final String url);
	
	/**
	 * Gets an instance of a ExerciseService.
	 *
	 * @return singleton instance
	 */
	static ExerciseService getInstance() {
		return ServiceManager.getService(ExerciseService.class);
	}
}
