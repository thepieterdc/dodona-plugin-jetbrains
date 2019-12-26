/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.project.builders;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.project.DodonaModuleBuilder;
import io.github.thepieterdc.dodona.plugin.project.ui.CourseWizardStep;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.resources.Course;

/**
 * Utilities for building a module.
 */
enum BuilderUtils {
	;
	
	/**
	 * Creates a wizard step that allows the user to select a course.
	 *
	 * @param builder the module builder
	 * @return the created step
	 */
	static ModuleWizardStep createCourseSelectionStep(final DodonaModuleBuilder builder) {
		final DodonaAuthenticator auth = DodonaAuthenticator.getInstance();
		final DodonaExecutorHolder executor = auth.getExecutor();
		return new CourseWizardStep(builder, executor);
	}
	
	/**
	 * Finishes the creation of a module.
	 *
	 * @param module the created module
	 * @param course the selected course
	 */
	static void finish(final Module module, final Course course) {
		// Find the newly created project.
		final Project project = module.getProject();
		
		// Set the course id.
		DodonaProjectSettings.getInstance(project).setCourseId(course.getId());
		
		// Save the project.
		project.save();
	}
}
