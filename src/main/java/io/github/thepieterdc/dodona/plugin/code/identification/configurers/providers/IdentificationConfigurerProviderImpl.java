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
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentifier;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import io.github.thepieterdc.dodona.resources.ProgrammingLanguage;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

/**
 * Implementation of a identification configurer provider.
 */
public class IdentificationConfigurerProviderImpl implements IdentificationConfigurerProvider {
	private static final Collection<IdentificationConfigurer> configurers = Arrays.asList(
		CodeIdentifier.values()
	);
	
	/**
	 * Gets the identification configurer to use given a file extension.
	 *
	 * @param extension the file extension
	 * @return the identification configurer to use
	 */
	@Nonnull
	private static Optional<IdentificationConfigurer> getConfigurerByExtension(@Nonnull final String extension) {
		return configurers.stream()
			.filter(config -> config.getFileExtension().equalsIgnoreCase(extension))
			.findAny();
	}
	
	@Nonnull
	@Override
	public Optional<IdentificationConfigurer> getConfigurer(@Nonnull final Language language) {
		return Optional.of(language)
			.map(Language::getAssociatedFileType)
			.map(FileType::getDefaultExtension)
			.flatMap(IdentificationConfigurerProviderImpl::getConfigurerByExtension);
	}
	
	@Nonnull
	@Override
	public Optional<IdentificationConfigurer> getConfigurer(@Nonnull final ProgrammingLanguage language) {
		return getConfigurerByExtension(language.getExtension());
	}
	
	@Nonnull
	@Override
	public Optional<IdentificationConfigurer> getConfigurer(@Nonnull String fileName) {
		return configurers.stream()
			.filter(config -> fileName.toLowerCase(Locale.getDefault()).endsWith(config.getFileExtension()))
			.findAny();
	}
}
