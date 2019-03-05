/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification.configurers.providers;

import be.ugent.piedcler.dodona.plugin.code.identification.IdentificationConfigurer;
import be.ugent.piedcler.dodona.plugin.code.identification.IdentificationConfigurerProvider;
import be.ugent.piedcler.dodona.plugin.code.identification.configurers.JavaIdentificationConfigurer;
import be.ugent.piedcler.dodona.plugin.code.identification.configurers.JavaScriptIdentificationConfigurer;
import be.ugent.piedcler.dodona.plugin.code.identification.configurers.PythonIdentificationConfigurer;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Implementation of a identification configurer provider.
 */
public class IdentificationConfigurerProviderImpl implements IdentificationConfigurerProvider {
	private static final Collection<IdentificationConfigurer> configurers = Arrays.asList(
		new JavaIdentificationConfigurer(),
		new JavaScriptIdentificationConfigurer(),
		new PythonIdentificationConfigurer()
	);
	
	/**
	 * Gets the identification configurer to use given a file extension.
	 *
	 * @param extension the file extension
	 * @return the identification configurer to use
	 */
	@Nonnull
	private static Optional<IdentificationConfigurer> getConfigurerByExtension(@Nonnull final String extension) {
	
	}
}