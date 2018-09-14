/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.warnings;

import be.ugent.piedcler.dodona.dto.Submission;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;

/**
 * The user submitted a solution but it was incorrect
 */
public class IncorrectSubmissionException extends WarningMessageException {
	private static final long serialVersionUID = 1783030686038781502L;
	
	/**
	 * IncorrectSubmissionException constructor.
	 */
	public IncorrectSubmissionException(final Submission submission) {
		super(String.format(
				"The submission to \"%s\" was incorrect. <a href=\"%s\">More details</a>.",
				submission.getExercise().getName(),
				submission.getUrl()
		));
	}
}
