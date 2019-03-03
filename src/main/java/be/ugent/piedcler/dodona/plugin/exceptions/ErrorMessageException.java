package be.ugent.piedcler.dodona.plugin.exceptions;/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */

import be.ugent.piedcler.dodona.exceptions.DodonaException;

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
