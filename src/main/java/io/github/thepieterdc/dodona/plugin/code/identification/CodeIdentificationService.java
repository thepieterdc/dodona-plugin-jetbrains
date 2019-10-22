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

import javax.annotation.Nonnull;

/**
 * Service that sets the identification string in the code.
 */
@FunctionalInterface
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
	 * @return the instance
	 */
	@Nonnull
	static CodeIdentificationService getInstance() {
		return ServiceManager.getService(CodeIdentificationService.class);
	}
}
