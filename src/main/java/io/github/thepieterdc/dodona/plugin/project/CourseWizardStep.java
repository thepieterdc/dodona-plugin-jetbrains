/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.project.ui.CourseWizardPanel;

import javax.swing.*;

/**
 * Wizard step: Select course.
 */
public class CourseWizardStep extends ModuleWizardStep {
	private final DodonaModuleBuilder builder;
	
	private final CourseWizardPanel panel;
	
	/**
	 * CourseWizardStep constructor.
	 *
	 * @param builder module builder
	 */
	public CourseWizardStep(final DodonaModuleBuilder builder) {
		super();
		this.builder = builder;
		this.panel = new CourseWizardPanel();
	}
	
	@Override
	public JComponent getComponent() {
		return this.panel;
	}
	
	@Override
	public void updateDataModel() {
		this.builder.setCourse(Integer.parseInt(this.panel.getCourse()));
	}
	
	@Override
	public boolean validate() throws ConfigurationException {
		if (this.panel.getCourse().isEmpty()) {
			throw new ConfigurationException(DodonaBundle.message("module.course.blank"));
		}
		return super.validate();
	}
}
