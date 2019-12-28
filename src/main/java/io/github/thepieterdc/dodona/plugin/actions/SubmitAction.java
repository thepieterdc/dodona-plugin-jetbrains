/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.actions;

import com.intellij.codeInsight.CodeSmellInfo;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.messages.MessageBus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.code.analysis.CodeAnalysisService;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentificationService;
import io.github.thepieterdc.dodona.plugin.exceptions.CancelledException;
import io.github.thepieterdc.dodona.plugin.exceptions.error.UnidentifiedCodeException;
import io.github.thepieterdc.dodona.plugin.exercise.CurrentExerciseListener;
import io.github.thepieterdc.dodona.plugin.tasks.SubmitSolutionTask;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
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
		final Project project = Objects.requireNonNull(e.getProject());
		try {
			e.getPresentation().setEnabled(false);
			submit(project, true);
		} finally {
			e.getPresentation().setEnabled(true);
		}
	}
	
	/**
	 * Finds a syntax error in the code if it exists.
	 *
	 * @param project the current project
	 * @param psiFile the file to analyse
	 * @return the error if any
	 */
	@Nonnull
	private static Optional<CodeSmellInfo> findSyntaxError(final Project project,
	                                                       final PsiFile psiFile) {
		final CodeAnalysisService codeAnalysisSrv = CodeAnalysisService
			.getInstance(project);
		return Optional.of(psiFile)
			.map(PsiFile::getVirtualFile)
			.map(codeAnalysisSrv::errors)
			.flatMap(smells -> smells.stream().findFirst());
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
	                                            final Document document) {
		return Optional.ofNullable(project)
			.map(PsiDocumentManager::getInstance)
			.map(mgr -> mgr.getPsiFile(document));
	}
	
	/**
	 * Submits the current file to Dodona.
	 *
	 * @param project     the current project
	 * @param checkSyntax true to validate the syntax of the file
	 */
	private static void submit(final Project project,
	                           final boolean checkSyntax) {
		// Get the document.
		final Document document = getDocument(project).orElseThrow(RuntimeException::new);
		
		// Get the file.
		final PsiFile file = getPsiFile(project, document).orElseThrow(RuntimeException::new);
		
		try {
			// Get the code.
			final String code = document.getText();
			
			// Validate the syntax.
			if (checkSyntax) {
				final Optional<CodeSmellInfo> syntaxError = findSyntaxError(project, file);
				syntaxError.ifPresent(error -> {
					final int result = Messages.showYesNoDialog(
						project,
						DodonaBundle.message("dialog.submit.syntax_errors.message", error.getStartLine() + 1),
						DodonaBundle.message("dialog.submit.syntax_errors.title"),
						Messages.getWarningIcon()
					);
					if (result == Messages.NO) {
						throw new CancelledException();
					}
				});
			}
			
			// Create a new SubmitTask and execute it.
			SubmitSolutionTask.create(project, code).execute();
		} catch (final CancelledException ignored) {
		} catch (final UnidentifiedCodeException ignored) {
			// Create a bus to broadcast the event when the exercise is identified.
			final MessageBus bus = project.getMessageBus();
			
			// Identify the exercise.
			CodeIdentificationService.getInstance(project).identify(document).whenComplete((id, ex) -> {
				if (id != null) {
					bus.syncPublisher(CurrentExerciseListener.CHANGED_TOPIC)
						.onCurrentExercise(file.getVirtualFile(), id);
					submit(project, false);
				}
			});
		}
	}
	
	@Override
	public void update(@NotNull final AnActionEvent e) {
		e.getPresentation().setEnabled(getDocument(e.getProject()).isPresent());
	}
}