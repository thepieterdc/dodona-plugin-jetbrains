/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.actions;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Groups all actions to allow a variable set of actions to be displayed.
 */
public class DodonaActionsGroup extends ActionGroup implements DumbAware {
	private static final AnAction[] defaultActions = {
		Separator.create(),
		new SubmitAction()
	};
	
	@NotNull
	@Override
	public AnAction[] getChildren(@Nullable final AnActionEvent e) {
		// Ensure the action is valid.
		if (e == null) {
			return AnAction.EMPTY_ARRAY;
		}
		
		// Ensure the project is valid.
		final Project project = e.getProject();
		if (project == null) {
			return AnAction.EMPTY_ARRAY;
		}
		
		return defaultActions.clone();
	}
	
	@Override
	public void update(@NotNull final AnActionEvent e) {
		final Project project = e.getProject();
		e.getPresentation().setEnabledAndVisible(project != null);
	}
}
