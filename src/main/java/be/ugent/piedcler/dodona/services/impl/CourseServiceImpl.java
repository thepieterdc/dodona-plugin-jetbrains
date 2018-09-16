/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.CourseResponse;
import be.ugent.piedcler.dodona.api.responses.RootResponse;
import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.services.CourseService;
import be.ugent.piedcler.dodona.settings.SettingsHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation class for CourseService.
 */
public class CourseServiceImpl implements CourseService {
	private final Map<Long, Course> cache;
	
	/**
	 * CourseServiceImpl constructor.
	 */
	public CourseServiceImpl() {
		this.cache = new HashMap<>(5);
	}
	
	@Override
	public Course get(final long id) {
		return Optional.ofNullable(this.cache.get(id))
			.filter(course -> !course.getSeries().isEmpty())
			.orElseGet(() -> {
				final Course ret = CourseServiceImpl.getFromApi(id);
				this.cache.put(id, ret);
				return ret;
			});
	}
	
	/**
	 * Gets a course from the api.
	 *
	 * @param id the id of the course to fetch
	 * @return the course
	 */
	private static Course getFromApi(final long id) {
		final String url = Course.getUrl(id);
		return Http.get(url, CourseResponse.class).toCourse();
	}
	
	@Override
	public Collection<Course> getSubscribed() {
		return Http.get(SettingsHelper.getDodonaURL(), RootResponse.class)
			.getUser()
			.getSubscribedCourses()
			.stream()
			.map(CourseResponse::toCourse)
			.collect(Collectors.toSet());
	}
}
