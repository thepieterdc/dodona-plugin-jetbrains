/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.exceptions.warnings;

import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.Submission;

/**
 * The user submitted a solution but it was incorrect
 */
public class IncorrectSubmissionException extends WarningMessageException {
	private static final long serialVersionUID = 1783030686038781502L;
	
	/**
	 * IncorrectSubmissionException constructor.
	 */
	public IncorrectSubmissionException(final Submission submission, Exercise exercise) {
		super(String.format(
			"The submission to \"%s\" was incorrect. <a href=\"%s\">More details</a>.",
			exercise.getName(),
			submission.getUrl()
		));
	}
}
