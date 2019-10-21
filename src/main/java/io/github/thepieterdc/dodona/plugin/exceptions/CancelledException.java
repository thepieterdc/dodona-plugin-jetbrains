/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exceptions;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;

/**
 * Action has been cancelled by the user.
 */
public final class CancelledException extends RuntimeException {
	private static final long serialVersionUID = 8493202782174285992L;
	
	/**
	 * CancelledException constructor.
	 */
	public CancelledException() {
		super(DodonaBundle.message("exceptions.cancelled"));
	}
}
