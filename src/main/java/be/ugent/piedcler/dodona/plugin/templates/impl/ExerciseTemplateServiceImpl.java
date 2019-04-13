/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.templates.impl;

import be.ugent.piedcler.dodona.plugin.templates.Template;
import be.ugent.piedcler.dodona.plugin.templates.ExerciseTemplateService;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.ProgrammingLanguage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of an ExerciseTemplateService.
 */
public class ExerciseTemplateServiceImpl implements ExerciseTemplateService {
	private static final String[] TEMPLATES = {"doctest.py"};
	
	/**
	 * Lists all templates ending in a given extension.
	 *
	 * @param extension the extension to match
	 * @return the matching templates
	 */
	private Collection<Template> listTemplates(final String extension) {
		return Arrays.stream(TEMPLATES)
			.filter(t -> t.endsWith(extension.toLowerCase(Locale.getDefault())))
			.map(t -> this.getClass().getClassLoader().getResourceAsStream(String.format("/templates/%s", t)))
			.map(stream -> loadTemplate(stream).orElse(null))
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
	}
	
	@Nonnull
	@Override
	public List<Template> listTemplates(final Exercise exercise) {
		final Stream<Template> byExtension = exercise.getProgrammingLanguage()
			.map(ProgrammingLanguage::getExtension)
			.map(this::listTemplates)
			.map(Collection::stream)
			.orElseGet(Stream::empty);
		
		final Stream<Template> fromBoilerplate = exercise.getBoilerplate()
			.map(Template::boilerplate)
			.map(Stream::of)
			.orElseGet(Stream::empty);
		
		return Stream.concat(byExtension, fromBoilerplate)
			.sorted()
			.collect(Collectors.toList());
	}
	
	/**
	 * Loads and parses the given template.
	 *
	 * @param stream the stream to parse
	 * @return the parsed template
	 */
	private static Optional<Template> loadTemplate(@Nullable final InputStream stream) {
		if (stream == null) {
			return Optional.empty();
		}
		
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			final List<String> contentsList = reader.lines().collect(Collectors.toList());
			
			final String name = contentsList.get(0).substring(6);
			
			final String contents = String.join("\n", contentsList.subList(1, contentsList.size()));
			
			return Optional.of(Template.create(name, contents));
		} catch (final IOException ex) {
			return Optional.empty();
		}
	}
}
