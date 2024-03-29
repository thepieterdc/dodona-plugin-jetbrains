/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.feedback;

import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.resources.activities.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import javax.annotation.Nonnull;

/**
 * Renders feedback about submissions.
 */
@FunctionalInterface
public interface FeedbackService {
	/**
	 * Gets an instance of the FeedbackService.
	 *
	 * @param project the current project
	 * @return the instance
	 */
	@Nonnull
	static FeedbackService getInstance(final Project project) {
		return project.getService(FeedbackService.class);
	}
	
	/**
	 * Notifies the user about the submission result.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	void notify(Exercise exercise, SubmissionInfo submission);
}
