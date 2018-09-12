/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.exceptions.warnings;

import be.ugent.piedcler.dodona.exceptions.WarningMessageException;

/**
 * The api key was not yet configured in the settings.
 */
public class MissingApiKeyException extends WarningMessageException {
	private static final long serialVersionUID = 4739214540153438897L;
	
	/**
	 * MissingApiKeyException constructor.
	 */
	public MissingApiKeyException() {
		super("Please set your api token in the plugin settings. See the README at <a href=\"https://github.com/thepieterdc/ugent-dodona/\">https://github.com/thepieterdc/ugent-dodona/</a> for instructions.");
	}
}
