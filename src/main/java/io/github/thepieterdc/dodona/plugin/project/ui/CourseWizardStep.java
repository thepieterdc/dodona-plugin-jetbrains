/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project.ui;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.project.DodonaModuleBuilder;

import javax.swing.*;

/**
 * Wizard step: Select course.
 */
public class CourseWizardStep extends ModuleWizardStep {
	private final DodonaModuleBuilder builder;
	private final DodonaExecutorHolder executor;
	
	private CourseListPanel coursesPanel;
	private JPanel rootPanel;
	
	/**
	 * CourseWizardStep constructor.
	 *
	 * @param builder  module builder
	 * @param executor request executor
	 */
	public CourseWizardStep(final DodonaModuleBuilder builder,
	                        final DodonaExecutorHolder executor) {
		super();
		this.builder = builder;
		this.executor = executor;
		this.coursesPanel.requestUpdate();
	}
	
	public void createUIComponents() {
		this.coursesPanel = new CourseListPanel(this.executor);
	}
	
	@Override
	public JComponent getComponent() {
		return this.rootPanel;
	}
	
	@Override
	public void updateDataModel() {
		this.coursesPanel.getSelectedCourse().ifPresent(this.builder::setCourse);
	}
	
	@Override
	public boolean validate() throws ConfigurationException {//
		if (!this.coursesPanel.getSelectedCourse().isPresent()) {
			throw new ConfigurationException(DodonaBundle.message("module.course.blank"));
		}
		
		return super.validate();
	}
}
