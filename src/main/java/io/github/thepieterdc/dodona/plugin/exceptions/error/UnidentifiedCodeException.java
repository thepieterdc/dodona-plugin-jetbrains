/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exceptions.error;

import io.github.thepieterdc.dodona.exceptions.DodonaException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;

/**
 * Code is not yet identified.
 */
public final class UnidentifiedCodeException extends DodonaException {
	private static final long serialVersionUID = -3401236427063000618L;
	
	/**
	 * UnidentifiedCodeException constructor.
	 */
	public UnidentifiedCodeException() {
		super(DodonaBundle.message("exceptions.error.unidentified_code"));
	}
}
