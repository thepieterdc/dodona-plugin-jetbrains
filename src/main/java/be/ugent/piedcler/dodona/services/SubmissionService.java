/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.dto.Submission;
import com.intellij.openapi.components.ServiceManager;

/**
 * Service for Series entities.
 */
public interface SubmissionService extends ResourceService<Submission> {
	/**
	 * Gets an instance of a SubmissionService.
	 *
	 * @return singleton instance
	 */
	static SubmissionService getInstance() {
		return ServiceManager.getService(SubmissionService.class);
	}
	
	/**
	 * Submits a solution.
	 *
	 * @param solution the solution to submit
	 * @return the submission
	 */
	Submission submit(final Solution solution);
}
