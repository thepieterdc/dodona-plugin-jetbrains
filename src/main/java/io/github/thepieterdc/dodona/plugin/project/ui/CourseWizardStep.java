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
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.ui.AsyncProcessIcon;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.project.DodonaModuleBuilder;
import io.github.thepieterdc.dodona.plugin.ui.resources.course.TabbedCourseList;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;

/**
 * Wizard step: Select course.
 */
public class CourseWizardStep extends ModuleWizardStep {
	@NonNls
	private static final String COURSES_CARD_COURSES = "COURSES_COURSES";
	@NonNls
	private static final String COURSES_CARD_LOADING = "COURSES_LOADING";
	
	private final DodonaModuleBuilder builder;
	private final DodonaExecutor executor;
	
	private final AsyncProcessIcon loadingIcon;
	
	private final TabbedCourseList coursesList;
	
	private JPanel coursesPanel;
	private JPanel rootPanel;
	
	/**
	 * CourseWizardStep constructor.
	 *
	 * @param builder  module builder
	 * @param executor request executor
	 */
	public CourseWizardStep(final DodonaModuleBuilder builder, final DodonaExecutor executor) {
		super();
		this.builder = builder;
		this.coursesList = new TabbedCourseList();
		this.executor = executor;
		this.loadingIcon = new AsyncProcessIcon.Big(this.getClass() + ".loading");
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
		this.coursesPanel.add(this.coursesList, COURSES_CARD_COURSES);
		
		final JPanel loadingInnerPanel = new JPanel(new BorderLayout(10, 10));
		loadingInnerPanel.add(
			new JLabel(DodonaBundle.message("module.course.loading")),
			BorderLayout.PAGE_END
		);
		loadingInnerPanel.add(this.loadingIcon, BorderLayout.CENTER);
		
		final JPanel loadingPanel = new JPanel(new GridBagLayout());
		loadingPanel.add(loadingInnerPanel, new GridBagConstraints());
		
		this.coursesPanel.add(
			ScrollPaneFactory.createScrollPane(loadingPanel),
			COURSES_CARD_LOADING
		);
		this.showCard(COURSES_CARD_COURSES);
		
		this.requestUpdate();
	}
	
	/**
	 * Updates the courses list.
	 */
	private void requestUpdate() {
		this.loadingIcon.setBackground(this.coursesList.getBackground());
		this.showCard(COURSES_CARD_LOADING);
		
		this.executor.execute(dodona -> dodona.me().getSubscribedCourses())
			.whenComplete((courses, error) -> SwingUtilities.invokeLater(() -> {
				this.coursesList.setCourses(courses);
				this.showCard(COURSES_CARD_COURSES);
			}));
	}
	
	/**
	 * Shows the card with the given name in the coursesPanel.
	 *
	 * @param card the card to show
	 */
	private void showCard(@NonNls final String card) {
		((CardLayout) this.coursesPanel.getLayout()).show(this.coursesPanel, card);
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
