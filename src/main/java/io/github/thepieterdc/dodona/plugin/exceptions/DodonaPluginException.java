/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exceptions;

import io.github.thepieterdc.dodona.exceptions.DodonaException;

/**
 * Wrapper for custom exceptions
 */
public final class DodonaPluginException extends DodonaException {
	private static final long serialVersionUID = -6432908392790854190L;
	
	private final Throwable cause;
	
	/**
	 * PluginException constructor.
	 *
	 * @param message the exception message
	 * @param cause the root cause
	 */
	public DodonaPluginException(final String message, final Throwable cause) {
		super(message);
		this.cause = cause;
	}
	
	@Override
	public Throwable getCause() {
		return this.cause;
	}
}
