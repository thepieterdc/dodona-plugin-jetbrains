/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.accessdenied;

/**
 * A series that may not be accessed.
 */
public class SeriesAccessDeniedException extends ResourceAccessDeniedException {
	private static final long serialVersionUID = -3685006958508560629L;
	
	/**
	 * SeriesAccessDeniedException constructor.
	 */
	public SeriesAccessDeniedException() {
		super("You are not allowed to access this series.");
	}
}
