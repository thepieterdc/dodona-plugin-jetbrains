/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise.creation.impl;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementFactory;

import javax.annotation.Nonnull;

/**
 * Implementation of an ExerciseCreationService that can handle Java files.
 */
public class JavaExerciseCreationServiceImpl extends ExerciseCreationServiceImpl {
	private static final char EXT_SEP = '.';
	
	private final PsiElementFactory javaElementFactory;
	
	/**
	 * ExerciseCreationServiceImpl constructor.
	 *
	 * @param project the current project
	 */
	public JavaExerciseCreationServiceImpl(final Project project) {
		super(project);
		this.javaElementFactory = PsiElementFactory.getInstance(project);
	}
	
	@Nonnull
	@Override
	String generateContents(final String filename) {
		// Generate Java contents.
		if (filename.endsWith(JavaFileType.DOT_DEFAULT_EXTENSION)) {
			// Parse the class name from the file.
			final String className = filename.substring(0, filename.indexOf(EXT_SEP));
			
			// Get the Java class template.
			return this.javaElementFactory.createClass(className).getText();
		}
		return super.generateContents(filename);
	}
}
