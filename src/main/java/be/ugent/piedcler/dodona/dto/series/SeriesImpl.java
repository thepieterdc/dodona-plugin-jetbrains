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
import org.jetbrains.annotations.NotNull;

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
	
	@Override
	@NotNull
	public Series setExercises(final Collection<Exercise> nw) {
		this.exercises.clear();
		this.exercises.addAll(nw);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("Series{id=%d}", this.id);
	}
}
