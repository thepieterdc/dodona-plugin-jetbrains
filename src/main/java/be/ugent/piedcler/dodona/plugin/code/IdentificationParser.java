/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code;

import be.ugent.piedcler.dodona.plugin.dto.Solution;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Identifies the exercise url from the code.
 */
@FunctionalInterface
public interface IdentificationParser {
	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the exercise found
	 */
	@Nonnull
	Optional<Solution> identify(@Nonnull final CharSequence code);
}
