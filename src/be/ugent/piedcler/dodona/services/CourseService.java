/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Course;
import com.intellij.openapi.components.ServiceManager;

import java.util.Collection;

/**
 * Service for course entities.
 */
public interface CourseService extends ResourceService<Course> {
	/**
	 * Gets an instance of a CourseService.
	 *
	 * @return singleton instance
	 */
	static CourseService getInstance() {
		return ServiceManager.getService(CourseService.class);
	}
	
	/**
	 * Gets all courses the user is subscribed to.
	 *
	 * @return all courses
	 */
	Collection<Course> getSubscribed();
}
