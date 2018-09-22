/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.accessdenied;

import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;

/**
 * A requested resource may not be accessed.
 */
public abstract class ResourceAccessDeniedException extends ErrorMessageException {
	private static final long serialVersionUID = -181388328802609040L;
	
	/**
	 * ResourceAccessDeniedException constructor.
	 */
	ResourceAccessDeniedException(final String message) {
		super(message);
	}
}
