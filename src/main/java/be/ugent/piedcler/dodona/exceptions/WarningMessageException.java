/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions;

/**
 * Triggers a warning message.
 */
public abstract class WarningMessageException extends RuntimeException {
	private static final long serialVersionUID = -8659819533420936251L;
	
	/**
	 * WarningMessageException constructor.
	 *
	 * @param message the warning message
	 */
	protected WarningMessageException(final String message) {
		super(message);
	}
}
