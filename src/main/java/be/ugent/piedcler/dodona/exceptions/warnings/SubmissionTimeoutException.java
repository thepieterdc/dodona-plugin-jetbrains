/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.warnings;

import be.ugent.piedcler.dodona.dto.Submission;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;

/**
 * The user submitted a solution but it took too long to evaluate.
 */
public class SubmissionTimeoutException extends WarningMessageException {
	private static final long serialVersionUID = 1861364799339652909L;
	
	/**
	 * SubmissionTimeoutException constructor.
	 */
	public SubmissionTimeoutException(final Submission submission) {
		super(String.format(
			"The submission to \"%s\" took longer than expected to evalute. <a href=\"%s\">More details</a>.",
			submission.getExercise().getName(),
			submission.getUrl()
		));
	}
}
