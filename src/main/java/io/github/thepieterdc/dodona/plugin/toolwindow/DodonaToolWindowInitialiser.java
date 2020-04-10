/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;

/**
 * Displays the tool window when the project is opened.
 */
public class DodonaToolWindowInitialiser implements StartupActivity {
	@Override
	public void runActivity(final Project project) {
		// Determine whether the tool window should be shown now.
		project.getService(DodonaToolWindowFactory.class).update();
	}
}
