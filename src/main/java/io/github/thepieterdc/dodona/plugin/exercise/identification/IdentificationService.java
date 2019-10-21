/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.exercise.identification;

import com.intellij.openapi.components.ServiceManager;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Identifies an exercise.
 */
public interface IdentificationService {
	/**
	 * Gets an instance of the IdentificationService.
	 *
	 * @return the instance
	 */
	@Nonnull
	static IdentificationService getInstance() {
		return ServiceManager.getService(IdentificationService.class);
	}

	/**
	 * Identifies the exercise based on the code.
	 *
	 * @param code the code
	 * @return the identification if successful
	 */
	@Nonnull
	Optional<Identification> identify(final String code);
}
