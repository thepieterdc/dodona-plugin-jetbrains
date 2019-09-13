/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project;

import com.intellij.ide.util.projectWizard.ModuleBuilderListener;

/**
 * Builder for Dodona projects.
 */
public interface DodonaModuleBuilder extends ModuleBuilderListener {
	/**
	 * The weight of the module
	 */
	int WEIGHT = Integer.MAX_VALUE;
	
	/**
	 * Sets the selected course.
	 *
	 * @param course the id of the course to set
	 */
	void setCourse(int course);
}
