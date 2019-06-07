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
import java.util.function.Function;

/**
 * An identification configurer that can discern various languages.
 */
public enum LanguageIdentificationConfigurer implements IdentificationConfigurer {
	HTML("html", v -> String.format("<!-- %s -->", v)),
	JAVA("java", v -> String.format("// %s", v)),
	JAVASCRIPT("js", v -> String.format("// %s", v)),
	PYTHON("py", v -> String.format("# %s", v));
	
	private Function<String, String> commentFn;
	private String extension;
	
	/**
	 * Construct a helper for generating the submission files.
	 *
	 * @param extension the file extension for the kind of exercise
	 * @param commentFn how should the Identification line be generated
	 */
	LanguageIdentificationConfigurer(final String extension, final Function<String, String> commentFn) {
		this.extension = extension;
		this.commentFn = commentFn;
	}
	
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
	 * Get the language-specific extension for a LanguageIdentificationConfigurer
	 *
	 * @return The string containin the specific file extension
	 */
	@Nonnull
	@Override
	public String getFileExtension() {
		return extension;
	}
	
	/**
	 * Gets the identification comment line
	 *
	 * @param identification the identification of the exercise
	 * @return the line to write in the file
	 */
	@Nonnull
	String getIdentificationLine(@Nonnull final String identification) {
		return commentFn.apply(identification) + "\n";
	}
}
