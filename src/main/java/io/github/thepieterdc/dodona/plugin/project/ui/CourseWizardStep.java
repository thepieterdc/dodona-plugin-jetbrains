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
import io.github.thepieterdc.dodona.plugin.ui.resources.course.TabbedCourseList;
import io.github.thepieterdc.dodona.plugin.ui.util.PanelUtils;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

/**
 * Wizard step: Select course.
 */
public class CourseWizardStep extends ModuleWizardStep {
	@NonNls
	private static final String CARD_COURSES = "COURSES_COURSES";
	@NonNls
	private static final String CARD_LOADING = "COURSES_LOADING";
	
	private final DodonaModuleBuilder builder;
	private final DodonaExecutorHolder executor;
	
	private final TabbedCourseList coursesList;
	
	private JPanel coursesPanel;
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
		this.coursesList = new TabbedCourseList();
		this.executor = executor;
		this.initialize();
	}
	
	@Override
	public JComponent getComponent() {
		return this.rootPanel;
	}
	
	/**
	 * Initializes the course selection panel.
	 */
	private void initialize() {
		// Card: courses list.
		this.coursesPanel.add(this.coursesList, CARD_COURSES);
		
		// Card: loading spinner.
		this.coursesPanel.add(PanelUtils.createLoading(
			this.coursesPanel,
			this.getClass(),
			"module.course.loading"),
			CARD_LOADING
		);
		
		// Refresh the list of courses.
		this.requestUpdate();
	}
	
	/**
	 * Updates the courses list.
	 */
	private void requestUpdate() {
		PanelUtils.showCard(this.coursesPanel, CARD_LOADING);
		
		this.executor.getExecutor().execute(dodona -> dodona.me().getSubscribedCourses())
			.whenComplete((courses, error) -> SwingUtilities.invokeLater(() -> {
				this.coursesList.setCourses(courses);
				PanelUtils.showCard(this.coursesPanel, CARD_COURSES);
			}));
	}
	
	@Override
	public void updateDataModel() {
		this.coursesList.getSelectedCourse().ifPresent(this.builder::setCourse);
	}
	
	@Override
	public boolean validate() throws ConfigurationException {//
		if (!this.coursesList.getSelectedCourse().isPresent()) {
			throw new ConfigurationException(DodonaBundle.message("module.course.blank"));
		}
		
		return super.validate();
	}
}
