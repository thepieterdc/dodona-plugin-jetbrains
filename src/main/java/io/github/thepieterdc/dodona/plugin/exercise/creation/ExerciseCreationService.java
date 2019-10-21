/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise.creation;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;

import javax.annotation.Nonnull;

/**
 * Creates an exercise file.
 */
@FunctionalInterface
public interface ExerciseCreationService {
	/**
	 * Creates an exercise file.
	 *
	 * @param directory      the directory in which the file should be created
	 * @param identification the exercise identification
	 * @return the created file
	 */
	VirtualFile create(final PsiDirectory directory,
	                   final FullIdentification identification);
	
	/**
	 * Gets an instance of the ExerciseCreationService.
	 *
	 * @param project the active project
	 * @return the instance
	 */
	@Nonnull
	static ExerciseCreationService getInstance(final Project project) {
		return ServiceManager.getService(project, ExerciseCreationService.class);
	}
}
