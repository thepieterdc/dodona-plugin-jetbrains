/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.code.identification;

import com.intellij.openapi.editor.Document;
import io.github.thepieterdc.dodona.plugin.code.DodonaFileType;
import io.github.thepieterdc.dodona.resources.activities.Exercise;
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
	SQL(DodonaFileType.SQL, v -> String.format("-- %s", v));
	
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
	 * Gets the identification line to prepend.
	 *
	 * @param exercise the identified exercise
	 * @return the line to add to the file
	 */
	@SuppressWarnings("HardcodedLineSeparator")
	@Nonnull
	private String getIdentificationLine(final Exercise exercise) {
		return String.format("%s\n", this.commentFn.apply(exercise.getUrl()));
	}
	
	/**
	 * Prepends the identification line to the document.
	 *
	 * @param exercise the exercise to set
	 * @param document the document to modify
	 */
	@NonNls
	public void process(final Exercise exercise, @NonNls final Document document) {
		document.insertString(0, this.getIdentificationLine(exercise));
	}
	
	/**
	 * Processes the given file contents to include the identification string.
	 *
	 * @param exercise the exercise to set
	 * @param contents the original file contents
	 * @return the processed file contents
	 */
	@NonNls
	@Nonnull
	public String process(final Exercise exercise, @NonNls final String contents) {
		return String.format("%s%s", this.getIdentificationLine(exercise), contents);
	}
}
