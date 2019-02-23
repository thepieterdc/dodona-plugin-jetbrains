/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.exceptions;

/**
 * The requested operation has been cancelled by the user.
 */
public class CancellationException extends RuntimeException {
	private static final long serialVersionUID = 7576276879583083453L;
	
	/**
	 * CancellationException constructor.
	 *
	 * @param message the message
	 */
	public CancellationException(final String message) {
		super(message);
	}
}
