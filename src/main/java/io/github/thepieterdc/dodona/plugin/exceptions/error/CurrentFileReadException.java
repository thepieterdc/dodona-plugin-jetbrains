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
 * The current opened file could not be read.
 */
public final class CurrentFileReadException extends DodonaException {
	private static final long serialVersionUID = 6747543650128071349L;
	
	/**
	 * CurrentFileReadException constructor.
	 */
	public CurrentFileReadException() {
		super(DodonaBundle.message("exceptions.error.current_file_read"));
	}
}
