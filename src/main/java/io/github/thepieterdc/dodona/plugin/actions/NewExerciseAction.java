/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.actions;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.exceptions.CancelledException;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.plugin.exercise.creation.ExerciseCreationService;
import io.github.thepieterdc.dodona.plugin.tasks.IdentifyTask;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Action: Create a new file from a Dodona exercise.
 */
public class NewExerciseAction extends AnAction implements DumbAware {
	/**
	 * NewExerciseAction constructor.
	 */
	NewExerciseAction() {
		super(
			DodonaBundle.message("actions.new_exercise.text"),
			DodonaBundle.message("actions.new_exercise.description"),
			Icons.DODONA
		);
	}
	
	@Override
	public void actionPerformed(@NotNull final AnActionEvent e) {
		final Project project = Objects.requireNonNull(e.getProject());
		final IdeView view = Objects.requireNonNull(e.getData(LangDataKeys.IDE_VIEW));
		
		try {
			// Prompt the user to identify the exercise.
			final FullIdentification identification = IdentifyTask
				.create(Objects.requireNonNull(e.getProject()))
				.execute()
				.orElseThrow(CancelledException::new);
			
			// Get the current directory.
			final PsiDirectory directory = Optional
				.ofNullable(view.getOrChooseDirectory())
				.orElseThrow(CancelledException::new);
			
			// Create the file.
			final VirtualFile created = ExerciseCreationService
				.getInstance(project)
				.create(directory, identification);
			
			// Open the created file.
			FileEditorManager.getInstance(project).openFile(created, true);
		} catch (final CancelledException ex) {
			// Ignore.
		}
	}
	
	@Override
	public void update(@NotNull final AnActionEvent e) {
		final Project project = e.getProject();
		final IdeView view = e.getData(LangDataKeys.IDE_VIEW);
		
		final PsiDirectory[] directory = view != null ? view.getDirectories() : null;
		e.getPresentation().setVisible(project != null && directory != null && directory.length > 0);
	}
}