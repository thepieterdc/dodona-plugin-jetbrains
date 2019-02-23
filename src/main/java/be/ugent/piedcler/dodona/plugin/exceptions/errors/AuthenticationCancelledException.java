/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.exceptions.errors;

import be.ugent.piedcler.dodona.plugin.exceptions.CancellationException;

/**
 * Authentication was cancelled by the user.
 */
public final class AuthenticationCancelledException extends CancellationException {
	private static final long serialVersionUID = -5023770902565939895L;
	
	/**
	 * AuthenticationCancelledException constructor.
	 */
	public AuthenticationCancelledException() {
		super("Authentication was cancelled.");
	}
}
