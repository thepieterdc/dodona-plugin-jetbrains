/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification;

import be.ugent.piedcler.dodona.plugin.exceptions.warnings.UndetectableProgrammingLanguageException;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.ProgrammingLanguage;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Provides an appropriate identification configurer to use.
 */
public interface IdentificationConfigurerProvider {
	/**
	 * Gets the configurer, given multiple options.
	 *
	 * @param exercise the exercise
	 * @param file     the file
	 * @return the configurer to use
	 */
	@Nonnull
	default IdentificationConfigurer getConfigurer(@Nonnull final Exercise exercise,
	                                               @Nullable final PsiFile file) {
		final Optional<IdentificationConfigurer> fromExercise = exercise.getProgrammingLanguage()
			.flatMap(this::getConfigurer);
		final Optional<IdentificationConfigurer> fromLanguage = Optional.ofNullable(file).map(PsiFile::getLanguage)
			.flatMap(this::getConfigurer);
		final Supplier<IdentificationConfigurer> fromFile = () -> Optional.ofNullable(file)
			.map(PsiFile::getVirtualFile)
			.flatMap(this::getConfigurer)
			.orElseThrow(() -> new UndetectableProgrammingLanguageException(file != null ? file.getName() : "null"));
		
		return fromExercise.orElseGet(() -> fromLanguage.orElseGet(fromFile));
	}
	
	/**
	 * Gets a configurer based on a language.
	 *
	 * @param language the language
	 * @return the configurer to use
	 */
	@Nonnull
	Optional<IdentificationConfigurer> getConfigurer(@Nonnull Language language);
	
	/**
	 * Gets a configurer based on a programming language.
	 *
	 * @param language the programming language
	 * @return the configurer to use
	 */
	@Nonnull
	Optional<IdentificationConfigurer> getConfigurer(@Nonnull ProgrammingLanguage language);
	
	/**
	 * Gets a configurer based on a filename.
	 *
	 * @param fileName the name of the file
	 * @return the configurer to use
	 */
	@Nonnull
	Optional<IdentificationConfigurer> getConfigurer(@Nonnull String fileName);
	
	/**
	 * Gets a configurer based on a virtual file.
	 *
	 * @param file the virtual file
	 * @return the configurer to use
	 */
	@Nonnull
	default Optional<IdentificationConfigurer> getConfigurer(@Nonnull final VirtualFile file) {
		return this.getConfigurer(file.getName());
	}
}
