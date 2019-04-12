/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.templates.impl;

import be.ugent.piedcler.dodona.plugin.dto.Template;
import be.ugent.piedcler.dodona.plugin.templates.ExerciseTemplateService;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.ProgrammingLanguage;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Implementation of an ExerciseTemplateService.
 */
public class ExerciseTemplateServiceImpl implements ExerciseTemplateService {
	@Nonnull
	@Override
	public Collection<Template> listTemplates(Exercise exercise) {
		final String extension = exercise.getProgrammingLanguage()
			.map(ProgrammingLanguage::getExtension)
			.orElse("");
		
		return Template.forExtension(extension);
	}
}
