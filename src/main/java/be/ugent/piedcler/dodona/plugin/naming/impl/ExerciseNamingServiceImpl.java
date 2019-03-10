/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.naming.impl;

import be.ugent.piedcler.dodona.plugin.naming.ExerciseNamingService;
import be.ugent.piedcler.dodona.resources.Exercise;

import javax.annotation.Nonnull;

/**
 * Implementation of a naming service for exercises.
 */
public class ExerciseNamingServiceImpl implements ExerciseNamingService {
	@Nonnull
	@Override
	public String generateFileName(@Nonnull final Exercise exercise) {
		return "test";
	}
}
