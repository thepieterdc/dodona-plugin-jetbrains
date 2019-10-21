/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package io.github.thepieterdc.dodona.plugin.feedback;

import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.Submission;

/**
 * Service that provides the user with feedback about an solution.
 */
public interface FeedbackService {
	/**
	 * Sends a feedback notification for the given submission for the given
	 * exercise.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	void notify(Exercise exercise, Submission submission);
}
