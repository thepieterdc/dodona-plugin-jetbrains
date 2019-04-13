/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;

import javax.annotation.Nonnull;

/**
 * Creates tool windows to list submissions.
 */
public class SubmissionsToolWindowCondition implements Condition<Project> {
	@Override
	public boolean value(@Nonnull final Project project) {
		return false;
	}
}
