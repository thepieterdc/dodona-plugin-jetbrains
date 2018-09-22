/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.notfound;

import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;

/**
 * A requested resource could not be found.
 */
public abstract class ResourceNotFoundException extends ErrorMessageException {
	private static final long serialVersionUID = 6021871305543261463L;
	
	/**
	 * ResourceNotFoundException constructor.
	 */
	ResourceNotFoundException(final String message) {
		super(message);
	}
}