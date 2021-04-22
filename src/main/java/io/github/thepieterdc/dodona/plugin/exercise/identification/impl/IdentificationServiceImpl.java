/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise.identification.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.exercise.identification.IdentificationService;
import io.github.thepieterdc.dodona.plugin.notifications.ErrorReporter;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Series;
import io.github.thepieterdc.dodona.resources.activities.Activity;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

/**
 * Default implementation of an IdentificationService. Uses the API library.
 */
public class IdentificationServiceImpl implements IdentificationService {
	@Nonnull
	@Override
	@SuppressWarnings("HardcodedLineSeparator")
	public Optional<Identification> identify(final String code) {
		// Get the first line of the code.
		final Optional<String> firstLine = Arrays.stream(code.split("\n"))
			.findFirst();
		
		// Perform the identification.
		final Long course = firstLine.flatMap(Course::getId).orElse(null);
		final Long series = firstLine.flatMap(Series::getId).orElse(null);
		final Optional<Long> exercise = firstLine.flatMap(Activity::getId);
		
		// Get the identification.
		return exercise.map(id -> new Identification(course, series, id));
	}
	
	@Nonnull
	@Override
	public Optional<Identification> identify(final Project project,
	                                         final VirtualFile file) {
		try {
			return Optional.of(PsiManager.getInstance(project))
				.map(psiMgr -> psiMgr.findFile(file))
				.map(PsiElement::getText)
				.flatMap(this::identify);
		} catch (final Throwable t) {
			// Handle files that are too large.
			ErrorReporter.report(t);
			return Optional.empty();
		}
	}
}
