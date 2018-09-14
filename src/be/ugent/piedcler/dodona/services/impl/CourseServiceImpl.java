/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.CourseResponse;
import be.ugent.piedcler.dodona.api.responses.RootResponse;
import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.services.CourseService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
		final Course fromCache = this.cache.get(id);
		System.out.println(fromCache);
		
		if(fromCache == null || fromCache.getSeries().isEmpty()) {
			System.out.println("api");
			final Course fromApi = getFromApi(id);
			System.out.println(fromApi);
			System.out.println(fromApi.getSeries());
			this.cache.put(id, fromApi);
			return fromApi;
		} else {
			System.out.println("cache");
			System.out.println(fromCache.getSeries());
			return fromCache;
		}
	}
	
	/**
	 * Gets a course from the api.
	 *
	 * @param id the id of the course to fetch
	 * @return the course
	 */
	private static Course getFromApi(final long id) {
		final String url = String.format(Course.ENDPOINT_ID, id);
		return Http.get(url, CourseResponse.class).toCourse();
	}
	
	@Override
	public Collection<Course> getSubscribed() {
		return Http.get(Configuration.DODONA_URL, RootResponse.class)
				.getUser()
				.getSubscribedCourses()
				.stream()
				.map(CourseResponse::toCourse)
				.collect(Collectors.toSet());
	}
}
