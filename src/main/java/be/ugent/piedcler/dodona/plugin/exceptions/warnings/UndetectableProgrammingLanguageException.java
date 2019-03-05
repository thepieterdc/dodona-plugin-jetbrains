/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.exceptions.warnings;

import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;

import javax.annotation.Nonnull;

/**
 * The programming language was not supported by the plugin.
 */
public final class UndetectableProgrammingLanguageException extends WarningMessageException {
	private static final long serialVersionUID = -394251866039769979L;
	
	private final String fileName;
	
	/**
	 * UndetectableProgrammingLanguageException constructor.
	 *
	 * @param fileName the file name
	 */
	public UndetectableProgrammingLanguageException(@Nonnull final String fileName) {
		super("The programming language could not be detected from the filename (%s). Please report this error at the repository.");
		this.fileName = fileName;
	}
	
	/**
	 * Gets the name of the file.
	 *
	 * @return the name of the file
	 */
	@Nonnull
	public String getFileName() {
		return this.fileName;
	}
}
