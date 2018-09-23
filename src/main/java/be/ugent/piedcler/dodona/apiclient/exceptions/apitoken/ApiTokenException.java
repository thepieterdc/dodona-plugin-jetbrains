/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.exceptions.apitoken;

/**
 * An exception occurred because of the used API token.
 */
public abstract class ApiTokenException extends RuntimeException {
	private static final long serialVersionUID = 3858569380966628927L;
	
	/**
	 * ApiTokenException constructor.
	 */
	ApiTokenException(final String message) {
		super(message);
	}
}
