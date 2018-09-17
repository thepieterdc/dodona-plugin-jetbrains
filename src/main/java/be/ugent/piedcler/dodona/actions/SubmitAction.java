/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.actions;

import be.ugent.piedcler.dodona.code.ExerciseIdenifierImpl.CombinedExerciseIdentifier;
import be.ugent.piedcler.dodona.code.ExerciseIdenifierImpl.StructuredExerciseIdentifier;
import be.ugent.piedcler.dodona.code.ExerciseIdenifierImpl.URLExerciseIdentifier;
import be.ugent.piedcler.dodona.code.ExerciseIdentifier;
import be.ugent.piedcler.dodona.code.FileSubmissionPreprocessor;
import be.ugent.piedcler.dodona.code.preprocess.CombinedSubmissionPreprocessor;
import be.ugent.piedcler.dodona.code.preprocess.JavaFileSubmissionPreprocessor;
import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;
import be.ugent.piedcler.dodona.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.exceptions.warnings.ExerciseNotSetException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.tasks.SetExerciseTask;
import be.ugent.piedcler.dodona.tasks.SubmitSolutionTask;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {

	private final ExerciseIdentifier identifier = new CombinedExerciseIdentifier(
		new StructuredExerciseIdentifier(),
		new URLExerciseIdentifier()
	);

	private final FileSubmissionPreprocessor preprocessor =
		new CombinedSubmissionPreprocessor(new HashMap<>())
			.registerEntry(JavaLanguage.INSTANCE, new JavaFileSubmissionPreprocessor());

	@Override
	public void actionPerformed(@NotNull final AnActionEvent event) {
		final PsiFile file = event.getData(LangDataKeys.PSI_FILE);
		final String code = (file != null) ?
			preprocessor.preprocess((PsiFile) file.copy()).getText() : null;

		try {
			if (code != null) {
				final Solution solution = identifier.identify(code).map(sol -> sol.setCode(code))
					.orElseThrow(ExerciseNotSetException::new);
				ProgressManager.getInstance().run(new SubmitSolutionTask(event.getProject(), solution));
			} else {
				throw new CodeReadException();
			}
		} catch (final ExerciseNotSetException exception) {
			ProgressManager.getInstance().run(new SetExerciseTask(event.getProject()));
		} catch (final WarningMessageException warning) {
			NotificationReporter.warning(warning.getMessage());
		} catch (final ErrorMessageException error) {
			NotificationReporter.error(error.getMessage());
		}
	}
}
