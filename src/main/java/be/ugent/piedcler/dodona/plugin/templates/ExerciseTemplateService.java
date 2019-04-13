/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.templates;

import be.ugent.piedcler.dodona.resources.Exercise;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A service that provides templates for Exercises.
 */
@FunctionalInterface
public interface ExerciseTemplateService {
	/**
	 * Lists all templates that can be applied to a given exercise. This
	 * includes the Exercise boilerplate if it's available and nonempty.
	 *
	 * @param exercise the exercise
	 * @return the matching templates
	 */
	@Nonnull
	List<Template> listTemplates(final Exercise exercise);
}
