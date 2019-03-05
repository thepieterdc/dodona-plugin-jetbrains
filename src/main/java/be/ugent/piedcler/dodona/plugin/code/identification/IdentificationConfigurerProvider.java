/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification;

import be.ugent.piedcler.dodona.resources.ProgrammingLanguage;

import javax.annotation.Nonnull;

/**
 * Provides an appropriate identification configurer to use.
 */
public interface IdentificationConfigurerProvider {
	/**
	 * Gets a configurer based on a filename.
	 *
	 * @param fileName the name of the file
	 * @return the configurer to use
	 */
	@Nonnull
	IdentificationConfigurer getConfigurer(@Nonnull String fileName);
	
	/**
	 * Gets a configurer based on a programming language.
	 *
	 * @param language the programming language
	 * @return the configurer to use
	 */
	@Nonnull
	IdentificationConfigurer getConfigurer(@Nonnull ProgrammingLanguage language);
}
