/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Course;
import com.intellij.openapi.components.ServiceManager;

import java.util.Collection;
import java.util.List;

/**
 * Service for Course entities.
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
	List<Course> getSubscribed();
}
