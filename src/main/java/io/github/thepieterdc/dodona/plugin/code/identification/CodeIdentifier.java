/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.code.identification;

import io.github.thepieterdc.dodona.plugin.code.DodonaFileType;
import io.github.thepieterdc.dodona.resources.Exercise;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * Identifiers for exercises.
 */
@SuppressWarnings("HardCodedStringLiteral")
public enum CodeIdentifier {
	HASKELL(DodonaFileType.HASKELL, v -> String.format("-- %s", v)),
	HTML(DodonaFileType.HTML, v -> String.format("<!-- %s -->", v)),
	JAVA(DodonaFileType.JAVA, v -> String.format("// %s", v)),
	JAVASCRIPT(DodonaFileType.JAVASCRIPT, v -> String.format("// %s", v)),
	PROLOG(DodonaFileType.PROLOG, v -> String.format("%% %s", v)),
	PYTHON(DodonaFileType.PYTHON, v -> String.format("# %s", v));
	
	private final Function<? super String, String> commentFn;
	private final DodonaFileType fileType;
	
	/**
	 * CodeIdentifier constructor.
	 *
	 * @param fileType  type of file
	 * @param commentFn how should the Identification line be generated
	 */
	CodeIdentifier(final DodonaFileType fileType,
	               @NonNls final Function<? super String, String> commentFn) {
		this.commentFn = commentFn;
		this.fileType = fileType;
	}
	
	/**
	 * Gets the appropriate code identifier for the given file extension.
	 *
	 * @param extension the file extension
	 * @return the identifier, if found
	 */
	@Nonnull
	public static Optional<CodeIdentifier> getForExtension(final String extension) {
		return Arrays.stream(CodeIdentifier.values())
			.filter(identifier -> identifier.fileType.getExtension().equals(extension))
			.findAny();
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
