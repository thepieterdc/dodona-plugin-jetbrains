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
 * Triggers a warning message.
 */
public abstract class WarningMessageException extends DodonaException {
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
