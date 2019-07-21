/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.exceptions.warnings;


import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.Submission;

/**
 * The user submitted a solution but it took too long to evaluate.
 */
public final class SubmissionTimeoutException extends WarningMessageException {
	private static final long serialVersionUID = 1861364799339652909L;
	
	/**
	 * SubmissionTimeoutException constructor.
	 */
	public SubmissionTimeoutException(final Submission submission, final Exercise exercise) {
		super(String.format(
			"The submission to \"%s\" took longer than expected to evalute. <a href=\"%s\">More details</a>.",
			exercise.getName(),
			submission.getUrl()
		));
	}
}
