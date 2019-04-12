/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.notifications;

import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.submissions.Submission;

import javax.annotation.Nonnull;

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
	void notify(@Nonnull Exercise exercise, @Nonnull Submission submission);
}
