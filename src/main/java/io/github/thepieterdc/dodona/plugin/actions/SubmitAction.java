/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.actions;

import be.ugent.piedcler.dodona.plugin.code.identification.IdentificationConfigurerProvider;
import be.ugent.piedcler.dodona.plugin.code.preprocess.FileSubmissionPreprocessor;
import be.ugent.piedcler.dodona.plugin.code.preprocess.impl.CombinedSubmissionPreprocessor;
import be.ugent.piedcler.dodona.plugin.code.preprocess.impl.JavaFileSubmissionPreprocessor;
import be.ugent.piedcler.dodona.plugin.dto.Solution;
import be.ugent.piedcler.dodona.plugin.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.ExerciseNotSetException;
import be.ugent.piedcler.dodona.plugin.identification.Identification;
import be.ugent.piedcler.dodona.plugin.identification.IdentificationService;
import be.ugent.piedcler.dodona.plugin.notifications.Notifier;
import be.ugent.piedcler.dodona.plugin.tasks.SelectExerciseTask;
import be.ugent.piedcler.dodona.plugin.tasks.SubmitSolutionTask;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction;

/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {
	private final IdentificationConfigurerProvider idConfigurer;
	private final IdentificationService identificationSrv;
	
	private final FileSubmissionPreprocessor preprocessor =
		new CombinedSubmissionPreprocessor()
			.registerEntry(ServiceManager.getService(JavaFileSubmissionPreprocessor.class));
	
	/**
	 * SubmitAction constructor.
	 */
	public SubmitAction() {
		this.idConfigurer = ServiceManager.getService(IdentificationConfigurerProvider.class);
		this.identificationSrv = ServiceManager.getService(IdentificationService.class);
	}
	
	@Override
	public void actionPerformed(@NotNull final AnActionEvent event) {
		final Project project = Optional.ofNullable(event.getData(PlatformDataKeys.PROJECT))
			.orElseThrow(CodeReadException::new);
		
		final Document document = Optional.ofNullable(FileEditorManager.getInstance(project))
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument)
			.orElseThrow(CodeReadException::new);
		
		try {
			final PsiFile file = Optional.ofNullable(PsiDocumentManager.getInstance(project))
				.map(mgr -> mgr.getPsiFile(document))
				.orElseThrow(CodeReadException::new);
			
			final String code = Optional.of(file)
				.map(PsiElement::copy)
				.map(e -> (PsiFile) e)
				.map(preprocessor::preprocess)
				.map(PsiElement::getText)
				.orElseThrow(CodeReadException::new);
			
			try {
				final Identification identification = this.identificationSrv.identify(code).orElseThrow(ExerciseNotSetException::new);
				final Solution solution = Solution.create(identification, code);
				
				ProgressManager.getInstance().run(new SubmitSolutionTask(project, event.getPresentation(), solution));
			} catch (final ExerciseNotSetException exception) {
				ProgressManager.getInstance().run(new SelectExerciseTask(project)).ifPresent(ex ->
					runWriteCommandAction(project, () -> idConfigurer.getConfigurer(ex, file).configure(document, ex.getUrl()))
				);
			}
		} catch (final RuntimeException ex) {
			Notifier.error(project, "Failed configuring exercise.", ex.getMessage(), ex);
		}
	}
	
	@Override
	public void update(final AnActionEvent event) {
		event.getPresentation().setEnabled(Optional.ofNullable(event.getData(PlatformDataKeys.PROJECT))
			.map(FileEditorManager::getInstance)
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument)
			.isPresent()
		);
	}
}
