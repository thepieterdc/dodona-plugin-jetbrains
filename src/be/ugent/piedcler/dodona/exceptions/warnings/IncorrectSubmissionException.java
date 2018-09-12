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

import be.ugent.piedcler.dodona.dto.submission.Submission;
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
				"The submission to %s was incorrect. <a href=\"%s\">More details</a>.",
				submission.getExercise(),
				submission.getUrl()
		));
	}
}
