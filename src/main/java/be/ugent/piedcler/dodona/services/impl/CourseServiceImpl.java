/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.CourseResponse;
import be.ugent.piedcler.dodona.api.responses.RootResponse;
import be.ugent.piedcler.dodona.api.responses.SeriesResponse;
import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.services.CourseService;
import be.ugent.piedcler.dodona.settings.SettingsHelper;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static be.ugent.piedcler.dodona.dto.Course.getUrl;

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
	@NotNull
	private static Course getFromApi(final long id) {
		final String url = getUrl(id);
		final Course course = Http.get(url, CourseResponse.class).toCourse();
		return course.setSeries(getSeriesFromApi(id));
	}
	
	/**
	 * Gets the series from a given course.
	 *
	 * @param course the course id
	 * @return the series in the course
	 */
	@NotNull
	private static List<Series> getSeriesFromApi(final long course) {
		return Stream.of(Http.get(Course.getSeriesUrl(course), SeriesResponse[].class))
			.map(SeriesResponse::toSeries)
			.collect(Collectors.toList());
	}
	
	@Override
	public List<Course> getSubscribed() {
		return Http.get(SettingsHelper.getDodonaURL(), RootResponse.class)
			.getUser()
			.getSubscribedCourses()
			.stream()
			.map(CourseResponse::toCourse)
			.collect(Collectors.toList());
	}
}
