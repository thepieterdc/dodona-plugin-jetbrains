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
 * The apiclient token was not yet configured in the settings.
 */
public class ApiTokenNotSetException extends ApiTokenException {
	private static final long serialVersionUID = 1651905808234392776L;
	
	/**
	 * ApiTokenNotSetException constructor.
	 */
	public ApiTokenNotSetException() {
		super("Please set your apiclient token in the plugin settings. See the README at <a href=\"https://github.com/thepieterdc/ugent-dodona/\">https://github.com/thepieterdc/ugent-dodona/</a> for instructions on how to do so.");
	}
}
