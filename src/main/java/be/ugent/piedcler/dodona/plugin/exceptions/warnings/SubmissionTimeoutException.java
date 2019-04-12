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
import be.ugent.piedcler.dodona.resources.submissions.Submission;

/**
 * The user submitted a solution but it took too long to evaluate.
 */
public final class SubmissionTimeoutException extends WarningMessageException {
	private static final long serialVersionUID = 1861364799339652909L;
	
	/**
	 * SubmissionTimeoutException constructor.
	 */
	public SubmissionTimeoutException(final Submission submission, Exercise exercise) {
		super(String.format(
			"The submission to \"%s\" took longer than expected to evalute. <a href=\"%s\">More details</a>.",
			exercise.getName(),
			submission.getUrl()
		));
	}
}
