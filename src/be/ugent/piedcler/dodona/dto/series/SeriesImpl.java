/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.series;

import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Series;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Implementation class of Series.
 */
public final class SeriesImpl implements Series {
	private final Collection<Exercise> exercises;
	private final long id;
	private final String name;
	
	/**
	 * SeriesImpl constructor.
	 *
	 * @param course the course of the series
	 * @param id     the id of the series
	 * @param name   the name of the series
	 */
	public SeriesImpl(final long id, final String name) {
		this.exercises = new HashSet<>(20);
		this.id = id;
		this.name = name;
	}
	
	@Override
	public Collection<Exercise> getExercises() {
		return Collections.unmodifiableCollection(this.exercises);
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the exercises in this series.
	 *
	 * @param exercises the exercises to set
	 * @return fluent setter
	 */
	public Series setExercises(final Collection<Exercise> exercises) {
		this.exercises.clear();
		this.exercises.addAll(exercises);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("Series{id=%d}", this.id);
	}
}
