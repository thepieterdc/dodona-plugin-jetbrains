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
 * The file that should be created, already exists.
 */
public final class FileAlreadyExistsException extends WarningMessageException {
	private static final long serialVersionUID = 2086238563554446197L;
	
	/**
	 * FileAlreadyExistsException constructor.
	 *
	 * @param filename the name of the file that already exists
	 */
	public FileAlreadyExistsException(@Nonnull final String filename) {
		super(String.format("The file '%s' could not be created, because it already exists.", filename));
	}
}
