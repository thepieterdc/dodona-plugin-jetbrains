/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.identification;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Identifies the exercise url from the code.
 */
public interface IdentificationService {
	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the found identification, if any
	 */
	@Nonnull
	Optional<Identification> identify(@Nonnull final CharSequence code);
	
	/**
	 * Identifies the current exercise given a file.
	 *
	 * @param file the file to process
	 * @return the found identification, if any
	 */
	@Nonnull
	Optional<Identification> identify(@Nonnull final PsiFile file);
	
	/**
	 * Identifies the current exercise given a virtual file.
	 *
	 * @param project the opened project
	 * @param file    the file to process
	 * @return the found identification, if any
	 */
	@Nonnull
	Optional<Identification> identify(@Nonnull final Project project, @Nonnull final VirtualFile file);
	
	/**
	 * Identifies the currently opened exercise.
	 *
	 * @param project the current project
	 * @return the found identification, if any
	 */
	@Nonnull
	Optional<Identification> identifyOpened(@Nonnull final Project project);
}
