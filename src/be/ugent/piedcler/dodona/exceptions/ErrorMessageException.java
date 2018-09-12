/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.exceptions;

/**
 * Triggers an error message.
 */
public abstract class ErrorMessageException extends RuntimeException {
	private static final long serialVersionUID = 2158622456618403362L;
	
	/**
	 * ErrorMessageException constructor.
	 *
	 * @param message the error message
	 */
	protected ErrorMessageException(final String message) {
		super(message);
	}
}
