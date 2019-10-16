/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.exceptions;

import io.github.thepieterdc.dodona.exceptions.DodonaException;

/**
 * Triggers an error message.
 */
public abstract class ErrorMessageException extends DodonaException {
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
