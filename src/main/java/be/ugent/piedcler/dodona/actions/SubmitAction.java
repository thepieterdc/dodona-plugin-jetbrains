/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.actions;

import be.ugent.piedcler.dodona.code.identifiers.getter.ExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.code.identifiers.getter.impl.CombinedExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.code.identifiers.getter.impl.StructuredExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.code.identifiers.getter.impl.URLExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.code.identifiers.setter.ExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.code.identifiers.setter.impl.CombinedExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.code.identifiers.setter.impl.JavaExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.code.identifiers.setter.impl.PythonExerciseIdentifierSetter;
import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.exceptions.warnings.ExerciseNotSetException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.tasks.SetExerciseTask;
import be.ugent.piedcler.dodona.tasks.SubmitSolutionTask;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;


/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {

	private final ExerciseIdentifierGetter identifierGetter =
		new CombinedExerciseIdentifierGetter()
			.registerIdentifier(new StructuredExerciseIdentifierGetter())
			.registerIdentifier(new URLExerciseIdentifierGetter());

	//	private final FileSubmissionPreprocessor preprocessor =
//		new CombinedSubmissionPreprocessor()
//			.registerEntry(JavaLanguage.INSTANCE, new JavaFileSubmissionPreprocessor());
//
	private final ExerciseIdentifierSetter identifierSetter =
		new CombinedExerciseIdentifierSetter()
			.registerEntry(ServiceManager.getService(JavaExerciseIdentifierSetter.class))
			.registerEntry(ServiceManager.getService(PythonExerciseIdentifierSetter.class));

	//private final ExerciseIdentifierSetter identifierSetter = ServiceManager.getService(ExerciseIdentifierSetter.class);

	@Override
	public void actionPerformed(@NotNull final AnActionEvent event) {
		final PsiFile file = event.getData(CommonDataKeys.PSI_FILE);

		//final String code = (file != null) ?
		//	preprocessor.preprocess((PsiFile) file.copy()).getText() : null;
		final String code = event.getData(PlatformDataKeys.FILE_TEXT);

		try {
			if (code != null) {
				final Solution solution = identifierGetter.identify(code).map(sol -> sol.setCode(code))
					.orElseThrow(ExerciseNotSetException::new);
				ProgressManager.getInstance().run(new SubmitSolutionTask(event.getProject(), solution));
			} else {
				throw new CodeReadException();
			}
		} catch (final ExerciseNotSetException exception) {
			ProgressManager.getInstance().run(
				new SetExerciseTask(event.getProject(), id -> this.identifierSetter.setIdentifier(file, id))
			);
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		}
	}
}
