/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification.configurers;

import be.ugent.piedcler.dodona.plugin.code.identification.IdentificationConfigurer;
import com.intellij.openapi.editor.Document;

import javax.annotation.Nonnull;

/**
 * An abstract identification configurer to share common logic.
 */
abstract class AbstractIdentificationConfigurer implements IdentificationConfigurer {
	@Override
	public void configure(@Nonnull final Document document,
	                      @Nonnull final String url) {
		document.insertString(0, this.getIdentificationLine(url));
	}
	
	@Nonnull
	@Override
	public String configure(@Nonnull final String code, @Nonnull final String url) {
		return this.getIdentificationLine(url) + code;
	}
	
	/**
	 * Gets the identification comment line
	 *
	 * @param identification the identification of the exercise
	 * @return the line to write in the file
	 */
	@Nonnull
	abstract String getIdentificationLine(@Nonnull final String identification);
}
