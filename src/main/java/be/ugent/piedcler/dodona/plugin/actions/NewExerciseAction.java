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
import be.ugent.piedcler.dodona.plugin.code.identification.IdentificationConfigurerProvider;
import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.FileAlreadyExistsException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.ProgrammingLanguageNotSetException;
import be.ugent.piedcler.dodona.plugin.notifications.Notifier;
import be.ugent.piedcler.dodona.plugin.tasks.SelectExerciseTask;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.ProgrammingLanguage;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

import static com.intellij.openapi.application.ActionsKt.runWriteAction;
import static com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction;

/**
 * Creates a new file from a Dodona exercise.
 */
public class NewExerciseAction extends AnAction implements DumbAware {
	private static final Logger logger = LoggerFactory.getLogger(NewExerciseAction.class);
	
	private final IdentificationConfigurerProvider idConfigurer;
	
	/**
	 * NewExerciseAction constructor.
	 */
	public NewExerciseAction() {
		super("Dodona Exercise", "Creates a new file from a Dodona exercise", Icons.DODONA);
		this.idConfigurer = ServiceManager.getService(IdentificationConfigurerProvider.class);
	}
	
	@Override
	public void actionPerformed(@Nonnull final AnActionEvent e) {
		final Project project = Objects.requireNonNull(e.getData(CommonDataKeys.PROJECT));
		final IdeView view = Objects.requireNonNull(e.getData(LangDataKeys.IDE_VIEW));
		
		try {
			ProgressManager.getInstance().run(new SelectExerciseTask(project)).ifPresent(ex -> create(project, view, ex));
		} catch (final WarningMessageException ex) {
			Notifier.warning(project, "Failed creating exercise", ex.getMessage(), ex);
		} catch (final RuntimeException ex) {
			Notifier.error(project, "Failed creating exercise", ex.getMessage(), ex);
		}
	}
	
	/**
	 * Creates a new file from a given exercise.
	 *
	 * @param project  the project
	 * @param view     current IDE view
	 * @param exercise the exercise
	 */
	private void create(@Nonnull final Project project,
	                    @Nonnull final IdeView view,
	                    @Nonnull final Exercise exercise) {
		final PsiDirectory directory = view.getOrChooseDirectory();
		if (directory == null) {
			return;
		}
		
		final String filename = generateFileName(exercise);
		final FileType filetype = FileTypeRegistry.getInstance().getFileTypeByFileName(filename);
		final String boilerplate = exercise.getBoilerplate().orElse("");
		
		Optional.ofNullable(directory.findFile(filename)).ifPresent(file -> {
			throw new FileAlreadyExistsException(filename);
		});
		
		final PsiFileFactory fileFactory = PsiFileFactory.getInstance(project);
		final PsiFile file = fileFactory.createFileFromText(filename, filetype, boilerplate);
		
		final VirtualFile virtualFile = runWriteAction(() -> (PsiFile) directory.add(file)).getVirtualFile();
		
		FileEditorManager.getInstance(project).openFile(virtualFile, true);
		
		final Document document = Optional.ofNullable(FileEditorManager.getInstance(project))
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument)
			.orElseThrow(CodeReadException::new);
		
		runWriteCommandAction(project, () -> idConfigurer.getConfigurer(exercise, file).configure(document, exercise.getUrl()));
	}
	
	/**
	 * Generates a filename for the given exercise.
	 *
	 * @param exercise the exercise
	 * @return the filename and extension
	 */
	@Nonnull
	private static String generateFileName(@Nonnull final Exercise exercise) {
		//TODO improve this, for example if it's a Java file, try to find the class name from the placeholder.
		//TODO if there is no placeholder, try the exercise name (cleaned!!), same goes for Python exercises
		final String extension = exercise.getProgrammingLanguage()
			.map(ProgrammingLanguage::getExtension)
			.orElseThrow(ProgrammingLanguageNotSetException::new);
		return String.valueOf(exercise.getId()) + '.' + extension;
	}
	
	@Override
	public void update(@Nonnull final AnActionEvent e) {
		final Project project = e.getData(CommonDataKeys.PROJECT);
		final IdeView view = e.getData(LangDataKeys.IDE_VIEW);
		
		final PsiDirectory[] directory = view != null ? view.getDirectories() : null;
		e.getPresentation().setVisible(directory != null && directory.length != 0 && project != null);
	}
}
