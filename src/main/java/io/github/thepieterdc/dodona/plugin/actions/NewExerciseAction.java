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
import com.intellij.openapi.actionSystem.ActionUpdateThread;
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
import io.github.thepieterdc.dodona.plugin.notifications.NotificationService;
import io.github.thepieterdc.dodona.plugin.notifications.SendableNotification;
import io.github.thepieterdc.dodona.plugin.tasks.IdentifyTask;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

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
                Icons.DODONA);
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Project project = Objects.requireNonNull(e.getProject());
        final IdeView view = Objects.requireNonNull(e.getData(LangDataKeys.IDE_VIEW));

        try {
            // Prompt the user to identify the exercise(s).
            final List<FullIdentification> identifications =
                    IdentifyTask.createMultiple(project).execute();
            if (identifications.isEmpty()) {
                throw new CancelledException();
            }

            // Get the current directory.
            final PsiDirectory directory =
                    Optional.ofNullable(view.getOrChooseDirectory()).orElseThrow(CancelledException::new);

            final ExerciseCreationService creation = ExerciseCreationService.getInstance(project);
            final FileEditorManager editors = FileEditorManager.getInstance(project);

            if (identifications.size() == 1) {
                // Create the file, prompting for a name.
                editors.openFile(creation.create(directory, identifications.get(0)), true);
                return;
            }

            // Create all files, skipping the ones that already exist.
            final List<VirtualFile> created = new ArrayList<>();
            final List<String> skipped = new ArrayList<>();
            for (final FullIdentification identification : identifications) {
                final Optional<VirtualFile> file = creation.createWithoutPrompt(directory, identification);
                if (file.isPresent()) {
                    created.add(file.get());
                } else {
                    skipped.add(identification.getExercise().getName());
                }
            }

            if (!created.isEmpty()) {
                editors.openFile(created.get(0), true);
            }

            final String title = DodonaBundle.message("creation.series.title");
            final NotificationService notifications = NotificationService.getInstance(project);
            if (skipped.isEmpty()) {
                notifications.send(SendableNotification.info(
                        title, DodonaBundle.message("creation.series.success", created.size())));
            } else {
                notifications.send(SendableNotification.error(
                        title,
                        DodonaBundle.message("creation.series.skipped", skipped.size(), String.join(", ", skipped))));
            }
        } catch (final CancelledException ex) {
            // Ignore.
        } catch (final Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        final Project project = e.getProject();
        final IdeView view = e.getData(LangDataKeys.IDE_VIEW);

        final PsiDirectory[] directory = view != null ? view.getDirectories() : null;
        e.getPresentation().setVisible(project != null && directory != null && directory.length > 0);
    }
}
