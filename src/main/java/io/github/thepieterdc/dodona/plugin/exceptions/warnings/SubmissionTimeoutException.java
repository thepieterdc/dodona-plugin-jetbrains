/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exceptions.warnings;

import io.github.thepieterdc.dodona.exceptions.DodonaException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.resources.activities.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.Submission;

/**
 * A submission took too long to evaluate.
 */
public final class SubmissionTimeoutException extends DodonaException {
	private static final long serialVersionUID = 794725189590066942L;
	
	/**
	 * SubmissionTimeoutException constructor.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	public SubmissionTimeoutException(final Exercise exercise,
	                                  final Submission submission) {
		super(DodonaBundle.message(
			"exceptions.warnings.submission_timeout",
			exercise.getName(),
			submission.getId()
		));
	}
}
