/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification;

import com.intellij.openapi.editor.Document;

import javax.annotation.Nonnull;

/**
 * Configures the identification in the code.
 */
public interface IdentificationConfigurer {
	/**
	 * Sets the identification in the code.
	 *
	 * @param document the document to modify
	 * @param url      the url to the exercise
	 */
	// TODO use the Identification class for this, instead of passing urls.
	void configure(@Nonnull Document document, @Nonnull String url);
	
	/**
	 * Sets the identification in the code.
	 *
	 * @param code the code to modify
	 * @param url  the url to the exercise
	 * @return the modified code
	 */
	// TODO use the Identification class for this, instead of passing urls.
	@Nonnull
	String configure(@Nonnull String code, @Nonnull String url);
	
	/**
	 * Gets the file extension that can be used with this configurer.
	 *
	 * @return the file extension
	 */
	@Nonnull
	String getFileExtension();
}
