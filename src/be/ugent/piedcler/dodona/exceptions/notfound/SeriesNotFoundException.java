/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.exceptions.notfound;

import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;

/**
 * A series that can not be found.
 */
public class SeriesNotFoundException extends ErrorMessageException {
	private static final long serialVersionUID = 5336065731460519195L;
	
	/**
	 * SeriesNotFoundException constructor.
	 */
	public SeriesNotFoundException() {
		super("The requested series could not be found.");
	}
}
