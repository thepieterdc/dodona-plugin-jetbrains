/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.exceptions.errors;

/**
 * Something went wrong while reading the code.
 */
public class CodeReadException extends RuntimeException {
	private static final long serialVersionUID = 2086238563554446197L;
	
	/**
	 * CodeReadException constructor.
	 */
	public CodeReadException() {
		super("Something went wrong while trying to read your code, try again.");
	}
}
