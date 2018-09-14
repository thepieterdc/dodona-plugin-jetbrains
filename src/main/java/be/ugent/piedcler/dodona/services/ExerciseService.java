/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Exercise;
import com.intellij.openapi.components.ServiceManager;

/**
 * Service for Exercise entities.
 */
public interface ExerciseService extends ResourceService<Exercise> {
	/**
	 * Gets an instance of a ExerciseService.
	 *
	 * @return singleton instance
	 */
	static ExerciseService getInstance() {
		return ServiceManager.getService(ExerciseService.class);
	}
}
