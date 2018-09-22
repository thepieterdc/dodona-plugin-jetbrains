/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.apitoken;

/**
 * The api token was configured but it was invalid.
 */
public class ApiTokenInvalidException extends ApiTokenException {
	private static final long serialVersionUID = 1651905808234392776L;
	
	/**
	 * ApiTokenInvalidException constructor.
	 */
	public ApiTokenInvalidException() {
		super("The configured API token is invalid. Please generate a new token, see the README at <a href=\"https://github.com/thepieterdc/ugent-dodona/\">https://github.com/thepieterdc/ugent-dodona/</a> for instructions on how to do so.");
	}
}
