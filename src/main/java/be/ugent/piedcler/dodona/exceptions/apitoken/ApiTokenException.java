/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.apitoken;

import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;

/**
 * An exception occurred because of the used API token.
 */
public abstract class ApiTokenException extends ErrorMessageException {
	private static final long serialVersionUID = 3858569380966628927L;
	
	/**
	 * ApiTokenException constructor.
	 */
	ApiTokenException(final String message) {
		super(message);
	}
}
