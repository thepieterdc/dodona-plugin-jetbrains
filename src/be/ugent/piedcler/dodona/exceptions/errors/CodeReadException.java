/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.exceptions.errors;

import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;

/**
 * Something went wrong while reading the code.
 */
public class CodeReadException extends ErrorMessageException {
	private static final long serialVersionUID = 2086238563554446197L;
	
	/**
	 * CodeReadException constructor.
	 */
	public CodeReadException() {
		super("Something went wrong while trying to read your code, try again.");
	}
}
