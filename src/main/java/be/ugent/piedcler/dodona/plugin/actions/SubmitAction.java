/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.actions;

import be.ugent.piedcler.dodona.apiclient.responses.Solution;
import be.ugent.piedcler.dodona.plugin.code.identifiers.getter.ExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.plugin.code.identifiers.getter.impl.CombinedExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.plugin.code.identifiers.getter.impl.StructuredExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.plugin.code.identifiers.getter.impl.URLExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.plugin.code.identifiers.setter.ExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.plugin.code.identifiers.setter.impl.CombinedExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.plugin.code.identifiers.setter.impl.JavaExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.plugin.code.identifiers.setter.impl.PythonExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.plugin.code.preprocess.FileSubmissionPreprocessor;
import be.ugent.piedcler.dodona.plugin.code.preprocess.impl.CombinedSubmissionPreprocessor;
import be.ugent.piedcler.dodona.plugin.code.preprocess.impl.JavaFileSubmissionPreprocessor;
import be.ugent.piedcler.dodona.plugin.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.ExerciseNotSetException;
import be.ugent.piedcler.dodona.plugin.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.plugin.tasks.SetExerciseTask;
import be.ugent.piedcler.dodona.plugin.tasks.SubmitSolutionTask;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction;
import static java.util.Optional.ofNullable;


/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {
	
	private final ExerciseIdentifierGetter identifierGetter =
		new CombinedExerciseIdentifierGetter()
			.registerIdentifier(new StructuredExerciseIdentifierGetter())
			.registerIdentifier(new URLExerciseIdentifierGetter());
	
	private final FileSubmissionPreprocessor preprocessor =
		new CombinedSubmissionPreprocessor()
			.registerEntry(ServiceManager.getService(JavaFileSubmissionPreprocessor.class));
	
	private final ExerciseIdentifierSetter identifierSetter =
		new CombinedExerciseIdentifierSetter()
			.registerEntry(ServiceManager.getService(JavaExerciseIdentifierSetter.class))
			.registerEntry(ServiceManager.getService(PythonExerciseIdentifierSetter.class));
	
	@Override
	public void actionPerformed(@NotNull final AnActionEvent event) {
		final Optional<PsiFile> optFile = ofNullable(event.getData(PlatformDataKeys.PSI_FILE));
		final Optional<Language> optLanguage = optFile.map(PsiElement::getLanguage);
		final Optional<Project> optProject = optFile.map(PsiFile::getProject);
		final Optional<Document> optDocument = optProject
			.map(PsiDocumentManager::getInstance)
			.flatMap(mgr -> optFile.map(mgr::getCachedDocument));
		
		final Optional<String> optText = optFile
			.map(PsiElement::copy)
			.map(e -> (PsiFile) e)
			.map(preprocessor::preprocess)
			.map(PsiElement::getText);
		
		try {
			
			final String text = optText.orElseThrow(CodeReadException::new);
			final Project project = optProject.orElseThrow(CodeReadException::new);
			final Solution solution = identifierGetter.identify(text)
				.map(sol -> sol.setCode(text))
				.orElseThrow(ExerciseNotSetException::new);
			
			ProgressManager.getInstance().run(new SubmitSolutionTask(project, event.getPresentation(), solution));
			
		} catch (final ExerciseNotSetException exception) {
			final Language language = optLanguage.orElseThrow(CodeReadException::new);
			final Project project = optProject.orElseThrow(CodeReadException::new);
			final Document document = optDocument.orElseThrow(CodeReadException::new);
			
			ProgressManager.getInstance().run(
				new SetExerciseTask(event.getProject(),
					id -> runWriteCommandAction(project, () -> identifierSetter.setIdentifier(language, document, id)))
			);
			
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		}
	}
}
