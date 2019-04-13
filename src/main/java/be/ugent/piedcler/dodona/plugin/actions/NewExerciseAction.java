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
import be.ugent.piedcler.dodona.plugin.templates.Template;
import be.ugent.piedcler.dodona.plugin.exceptions.UserAbortedException;
import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.FileAlreadyExistsException;
import be.ugent.piedcler.dodona.plugin.naming.ExerciseNamingService;
import be.ugent.piedcler.dodona.plugin.notifications.Notifier;
import be.ugent.piedcler.dodona.plugin.tasks.SelectExerciseTask;
import be.ugent.piedcler.dodona.plugin.templates.ExerciseTemplateService;
import be.ugent.piedcler.dodona.plugin.ui.templates.TemplateSelectionDialog;
import be.ugent.piedcler.dodona.resources.Exercise;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.ui.UIBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.intellij.openapi.application.ActionsKt.runWriteAction;

/**
 * Creates a new file from a Dodona exercise.
 */
public class NewExerciseAction extends AnAction implements DumbAware {
	private static final Logger logger = LoggerFactory.getLogger(NewExerciseAction.class);
	
	private final IdentificationConfigurerProvider idConfigurer;
	private final ExerciseNamingService namingService;
	private final ExerciseTemplateService templateService;
	
	/**
	 * NewExerciseAction constructor.
	 */
	public NewExerciseAction() {
		super("Dodona Exercise", "Creates a new file from a Dodona exercise", Icons.DODONA);
		this.idConfigurer = ServiceManager.getService(IdentificationConfigurerProvider.class);
		this.namingService = ServiceManager.getService(ExerciseNamingService.class);
		this.templateService = ServiceManager.getService(ExerciseTemplateService.class);
	}
	
	@Override
	public void actionPerformed(@Nonnull final AnActionEvent e) {
		final Project project = Objects.requireNonNull(e.getData(CommonDataKeys.PROJECT));
		final IdeView view = Objects.requireNonNull(e.getData(LangDataKeys.IDE_VIEW));
		
		try {
			ProgressManager.getInstance().run(new SelectExerciseTask(project)).ifPresent(ex -> create(project, view, ex));
		} catch (final UserAbortedException ex) {
			//
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
		
		final String filename = this.getFileName(exercise, directory);
		final FileType filetype = FileTypeRegistry.getInstance().getFileTypeByFileName(filename);
		final String template = this.getTemplate(project, exercise).map(Template::getContents).orElse("");
		
		Optional.ofNullable(directory.findFile(filename)).ifPresent(file -> {
			throw new FileAlreadyExistsException(filename);
		});
		
		final String code = this.idConfigurer.getConfigurer(exercise, null).configure(template, exercise.getUrl());
		
		final PsiFileFactory fileFactory = PsiFileFactory.getInstance(project);
		final PsiFile file = fileFactory.createFileFromText(filename, filetype, code);
		
		final VirtualFile virtualFile = runWriteAction(() -> (PsiFile) directory.add(file)).getVirtualFile();
		
		FileEditorManager.getInstance(project).openFile(virtualFile, true);
	}
	
	/**
	 * Gets a filename for the given exercise.
	 *
	 * @param exercise  the exercise
	 * @param directory the directory to save the file in, file may not yet exist
	 * @return the filename and extension
	 */
	@Nonnull
	private String getFileName(@Nonnull final Exercise exercise, final PsiDirectory directory) {
		String name = this.namingService.generateFilename(exercise).orElse(null);
		
		while (true) {
			name = Messages.showInputDialog(
				UIBundle.message("create.new.file.enter.new.file.name.prompt.text"),
				UIBundle.message("new.file.dialog.title"),
				Messages.getQuestionIcon(), name, null
			);
			
			if (name == null) {
				throw new UserAbortedException();
			}
			
			if ("".equals(name.trim())) {
				Messages.showMessageDialog(
					UIBundle.message("create.new.file.file.name.cannot.be.empty.error.message"),
					UIBundle.message("error.dialog.title"),
					Messages.getErrorIcon()
				);
				continue;
			}
			
			if (directory.findFile(name) != null) {
				Messages.showMessageDialog(
					"A file with this name already exists in the selected directory.",
					UIBundle.message("error.dialog.title"),
					Messages.getErrorIcon()
				);
				continue;
			}
			
			return name;
		}
	}
	
	/**
	 * Gets a template to create a new exercise file.
	 *
	 * @param exercise the exercise
	 * @return the chosen template, or null
	 */
	@Nonnull
	private Optional<Template> getTemplate(@Nonnull final Project project, @Nonnull final Exercise exercise) {
		final List<Template> templates = this.templateService.listTemplates(exercise);
		
		if (!templates.isEmpty()) {
			if (templates.size() == 1 && templates.get(0).isBoilerplate()) {
				return Optional.of(templates.get(0));
			}
			
			final TemplateSelectionDialog dialog = new TemplateSelectionDialog(project, templates);
			boolean useTemplate = dialog.showAndGet();
			
			return useTemplate ? dialog.getTemplate() : Optional.empty();
		}
		return Optional.empty();
	}
	
	@Override
	public void update(@Nonnull final AnActionEvent e) {
		final Project project = e.getData(CommonDataKeys.PROJECT);
		final IdeView view = e.getData(LangDataKeys.IDE_VIEW);
		
		final PsiDirectory[] directory = view != null ? view.getDirectories() : null;
		e.getPresentation().setVisible(directory != null && directory.length != 0 && project != null);
	}
}
