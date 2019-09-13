/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project.ui;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * UI for Wizard step: Select course.
 */
public final class CourseWizardPanel extends JPanel {
	private JTextField courseField;
	private JPanel root;
	
	/**
	 * CourseWizardPanel constructor.
	 */
	public CourseWizardPanel() {
		super(new BorderLayout(5, 5));
		this.add(this.root, BorderLayout.CENTER);
	}
	
	/**
	 * Gets the selected course.
	 *
	 * @return the course
	 */
	@Nonnull
	public String getCourse() {
		return this.courseField.getText();
	}
}
