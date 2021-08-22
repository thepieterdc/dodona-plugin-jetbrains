/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.exercise.identification;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
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
		return ApplicationManager.getApplication().getService(IdentificationService.class);
	}
	
	/**
	 * Identifies the exercise based on the code.
	 *
	 * @param code the code
	 * @return the identification if successful
	 */
	@Nonnull
	Optional<Identification> identify(String code);
	
	/**
	 * Identifies the current exercise given a virtual file.
	 *
	 * @param project the current project
	 * @param file    the file to process
	 * @return the identification if successful
	 */
	@Nonnull
	Optional<Identification> identify(Project project, VirtualFile file);
}
