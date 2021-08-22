/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.code.identification;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

/**
 * Service that sets the identification string in the code.
 */
public interface CodeIdentificationService {
	/**
	 * Gets the identifier for the given file name.
	 *
	 * @param fileName the file name
	 * @return the identifier
	 */
	@Nonnull
	CodeIdentifier getIdentifier(final String fileName);
	
	/**
	 * Gets an instance of the CodeIdentificationService.
	 *
	 * @param project the current project
	 * @return the instance
	 */
	@Nonnull
	static CodeIdentificationService getInstance(final Project project) {
		return project.getService(CodeIdentificationService.class);
	}
	
	/**
	 * Identifies the given document.
	 *
	 * @param document document to identify
	 */
	CompletableFuture<Identification> identify(Document document);
	
	/**
	 * Identifies the current opened file.
	 */
	void identifyCurrent();
}
