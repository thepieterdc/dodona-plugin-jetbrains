/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification;

import be.ugent.piedcler.dodona.plugin.dto.Identification;

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
	 * @return the found exercise, if any
	 */
	@Nonnull
	Optional<Identification> identify(@Nonnull final CharSequence code);
}
