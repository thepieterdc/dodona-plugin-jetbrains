/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.exceptions.warnings;

import be.ugent.piedcler.dodona.plugin.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;

/**
 * The apiclient key was not yet configured in the settings.
 */
public class MissingApiKeyException extends WarningMessageException {
	private static final long serialVersionUID = 4739214540153438897L;
	
	/**
	 * MissingApiKeyException constructor.
	 */
	public MissingApiKeyException() {
		super("Please set your apiclient token in the plugin settings. See the README at <a href=\"https://github.com/thepieterdc/ugent-dodona/\">https://github.com/thepieterdc/ugent-dodona/</a> for instructions.");
	}
}
