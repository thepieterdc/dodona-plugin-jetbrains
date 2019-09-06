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
 * The user aborted the ongoing action.
 */
public final class UserAbortedException extends RuntimeException {
	private static final long serialVersionUID = 4266029459834672125L;
	
	/**
	 * UserAbortedException constructor.
	 */
	public UserAbortedException() {
		super("Operation aborted.");
	}
}
