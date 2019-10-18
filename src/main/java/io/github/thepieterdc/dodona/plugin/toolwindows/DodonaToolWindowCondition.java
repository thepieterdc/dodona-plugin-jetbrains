/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;

/**
 * A condition for the tool window to be shown.
 */
public class DodonaToolWindowCondition implements Condition<Project> {
	@Override
	public boolean value(final Project project) {
		// Check whether the course id is set.
		if (project != null) {
			return DodonaProjectSettings.getInstance(project).getCourseId() > 0;
		}
		return false;
	}
}
