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

/**
 * The programming language was not set by the course owner.
 */
public final class ProgrammingLanguageNotSetException extends WarningMessageException {
	private static final long serialVersionUID = 2086238563554446197L;
	
	/**
	 * ProgrammingLanguageNotSetException constructor.
	 */
	public ProgrammingLanguageNotSetException() {
		super("The programming language was not found in the exercise. Please contact your course owner to set it correctly.");
	}
}
