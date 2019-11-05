/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.tasks.SubmitSolutionTask;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Action: Submit the current file to Dodona.
 */
public class SubmitAction extends AnAction {
	/**
	 * SubmitAction constructor.
	 */
	SubmitAction() {
		super(
			DodonaBundle.message("actions.submit.text"),
			DodonaBundle.message("actions.submit.description"),
			Icons.ACTION_SUBMIT
		);
	}
	
	@Override
	public void actionPerformed(@NotNull final AnActionEvent e) {
		// Get the document.
		final Optional<Document> optDocument = getDocument(e.getProject());
		
		// Get the code.
		final Optional<String> optCode = optDocument
			.flatMap(doc -> getPsiFile(e.getProject(), doc))
			.map(PsiElement::getText);
		
		// Create a new SubmitTask and execute it.
		optCode.ifPresent(code -> {
			try {
				e.getPresentation().setEnabled(false);
				SubmitSolutionTask
					.create(Objects.requireNonNull(e.getProject()), code)
					.execute();
			} finally {
				e.getPresentation().setEnabled(true);
			}
		});
	}
	
	/**
	 * Gets the currently opened document, if available.
	 *
	 * @param project the current project
	 * @return the current document if it is open
	 */
	@Nonnull
	private static Optional<Document> getDocument(@Nullable final Project project) {
		return Optional.ofNullable(project)
			.map(FileEditorManager::getInstance)
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument);
	}
	
	/**
	 * Gets the PsiFile of the given document.
	 *
	 * @param project  the current project
	 * @param document the current document
	 * @return the PsiFile if found
	 */
	@Nonnull
	private static Optional<PsiFile> getPsiFile(@Nullable final Project project,
	                                            @Nonnull final Document document) {
		return Optional.ofNullable(project)
			.map(PsiDocumentManager::getInstance)
			.map(mgr -> mgr.getPsiFile(document));
	}
	
	@Override
	public void update(@NotNull final AnActionEvent e) {
		e.getPresentation().setEnabled(getDocument(e.getProject()).isPresent());
	}
}
