/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.actions;

import be.ugent.piedcler.dodona.plugin.Icons;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

import javax.annotation.Nonnull;

/**
 * Creates a new file from a Dodona exercise.
 */
public class NewExerciseAction extends AnAction implements DumbAware {
	/**
	 * NewExerciseAction constructor.
	 */
	public NewExerciseAction() {
		super("Dodona Exercise", "Creates a new file from a Dodona exercise", Icons.DODONA);
	}
	
	@Override
	public void actionPerformed(@Nonnull final AnActionEvent e) {
		final Project project = e.getData(CommonDataKeys.PROJECT);
		final IdeView view = e.getData(LangDataKeys.IDE_VIEW);
		
//		ProgressManager.getInstance().run(
////			new NewExerciseTask(project)
//		);
	}
	
	@Override
	public void update(@Nonnull final AnActionEvent e) {
		final Project project = e.getData(CommonDataKeys.PROJECT);
		final IdeView view = e.getData(LangDataKeys.IDE_VIEW);
		
		final PsiDirectory[] directory = view != null ? view.getDirectories() : null;
		e.getPresentation().setVisible(directory != null && directory.length != 0 && project != null);
	}
}
