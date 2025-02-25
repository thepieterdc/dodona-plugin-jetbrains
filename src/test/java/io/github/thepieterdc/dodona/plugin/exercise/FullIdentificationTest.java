/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.exercise;

import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.activities.Exercise;
import io.github.thepieterdc.dodona.resources.Resource;
import io.github.thepieterdc.dodona.resources.Series;
import io.github.thepieterdc.random.numerical.RandomLongGenerator;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests FullIdentification.
 */
public class FullIdentificationTest {
	private static final RandomLongGenerator ids = RandomLongGenerator.positive();
	
	/**
	 * Creates a mock of a Resource.
	 *
	 * @param resourceClass the class to mock
	 * @param id            the id of the resource
	 * @param <T>           type class of the resource
	 * @return the mocked resource
	 */
	@Nonnull
	private static <T extends Resource> T mockResource(final Class<T> resourceClass,
	                                                   final long id) {
		final T mock = mock(resourceClass);
		when(mock.getId()).thenReturn(id);
		return mock;
	}
	
	/**
	 * Gets an instance of the identification.
	 *
	 * @param courseId   the id of the course
	 * @param seriesId   the id of the series
	 * @param exerciseId the id of the exercise
	 * @return the identification
	 */
	@Nonnull
	private static FullIdentification instance(@Nullable final Long courseId,
	                                           @Nullable final Long seriesId,
	                                           final Long exerciseId) {
		final Course course = Optional.ofNullable(courseId)
			.map(id -> mockResource(Course.class, id))
			.orElse(null);
		
		final Series series = Optional.ofNullable(seriesId)
			.map(id -> mockResource(Series.class, id))
			.orElse(null);
		
		final Exercise exercise = mockResource(Exercise.class, exerciseId);
		assertNotNull(exercise);
		
		final FullIdentification identification =
			new FullIdentification(course, series, exercise);
		assertNotNull(identification);
		return identification;
	}
	
	/**
	 * Tests FullIdentification#getCourse().
	 */
	@Test
	public void testGetCourse() {
		final long id = ids.generate();
		final FullIdentification identification =
			instance(id, ids.generate(), ids.generate());
		assertTrue(identification.getCourse().isPresent());
		assertEquals(id, identification.getCourse().get().getId());
		
		final FullIdentification identification2 =
			instance(null, ids.generate(), ids.generate());
		assertFalse(identification2.getCourse().isPresent());
	}
	
	/**
	 * Tests FullIdentification#getExercise().
	 */
	@Test
	public void testGetExercise() {
		final long id = ids.generate();
		final FullIdentification identification =
			instance(ids.generate(), ids.generate(), id);
		assertEquals(id, identification.getExercise().getId());
	}
	
	/**
	 * Tests FullIdentification#getSeries().
	 */
	@Test
	public void testGetSeries() {
		final long id = ids.generate();
		final FullIdentification identification =
			instance(ids.generate(), id, ids.generate());
		assertTrue(identification.getSeries().isPresent());
		assertEquals(id, identification.getSeries().get().getId());
		
		final FullIdentification identification2 =
			instance(ids.generate(), null, ids.generate());
		assertFalse(identification2.getSeries().isPresent());
	}
}