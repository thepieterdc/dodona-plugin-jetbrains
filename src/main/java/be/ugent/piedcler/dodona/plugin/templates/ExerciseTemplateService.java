/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.templates;

import be.ugent.piedcler.dodona.plugin.dto.Template;
import be.ugent.piedcler.dodona.resources.Exercise;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * A service that provides templates for Exercises.
 */
@FunctionalInterface
public interface ExerciseTemplateService {
	/**
	 * Lists all templates that can be applied to a given exercise.
	 *
	 * @param exercise the exercise
	 * @return the matching templates
	 */
	@Nonnull
	Collection<Template> listTemplates(final Exercise exercise);
}
