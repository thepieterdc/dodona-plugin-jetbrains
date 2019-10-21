/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.code.identification;

import io.github.thepieterdc.dodona.resources.Exercise;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * Identifiers for exercises.
 */
@SuppressWarnings("HardCodedStringLiteral")
public enum CodeIdentifier {
	HASKELL("hs", v -> String.format("-- %s", v)),
	HTML("html", v -> String.format("<!-- %s -->", v)),
	JAVA("java", v -> String.format("// %s", v)),
	JAVASCRIPT("js", v -> String.format("// %s", v)),
	PROLOG("pl", v -> String.format("%% %s", v)),
	PYTHON("py", v -> String.format("# %s", v));
	
	private final Function<? super String, String> commentFn;
	private final String extension;
	
	/**
	 * Construct a helper for generating the submission files.
	 *
	 * @param extension the file extension for the kind of exercise
	 * @param commentFn how should the Identification line be generated
	 */
	CodeIdentifier(@NonNls final String extension,
	               @NonNls final Function<? super String, String> commentFn) {
		this.commentFn = commentFn;
		this.extension = extension;
	}
	
	/**
	 * Get the default file extension.
	 *
	 * @return the file extension
	 */
	@Nonnull
	public String getFileExtension() {
		return this.extension;
	}
	
	/**
	 * Processes the given file contents to include the identification string.
	 *
	 * @param exercise the exercise to set
	 * @param contents the original file contents
	 * @return the processed file contents
	 */
	@SuppressWarnings("HardcodedLineSeparator")
	@NonNls
	@Nonnull
	public String process(final Exercise exercise, @NonNls final String contents) {
		final String identification = this.commentFn.apply(exercise.getUrl());
		return String.format("%s\n%s", identification, contents);
	}
}
