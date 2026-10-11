/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.plugin.ui.identification.IdentifyExerciseDialog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.swing.*;
import org.jetbrains.annotations.Nls;

/**
 * Prompts the user to identify the current exercise.
 */
public final class IdentifyTask extends AbstractDodonaResultTask<List<FullIdentification>> {
    private final DodonaExecutorHolder executor;

    private final boolean multiple;

    private List<FullIdentification> identifications = List.of();

    /**
     * SubmitSolutionTask constructor.
     *
     * @param project the current project
     * @param title   the title
     * @param multiple whether multiple exercises can be selected
     */
    private IdentifyTask(final Project project, final String title, final boolean multiple) {
        super(project, title);
        this.multiple = multiple;
        this.executor = DodonaAuthenticator.getInstance().getExecutor();
    }

    @Nonnull
    @Override
    protected List<FullIdentification> compute(final ProgressIndicator progress) {
        try {
            // Set the progressbar.
            progress.setIndeterminate(true);
            progress.setText(DodonaBundle.message("tasks.identify.progress"));

            // Perform the identification.
            SwingUtilities.invokeAndWait(this::showDialog);

            // Return the result if available.
            return this.identifications;
        } catch (final InterruptedException | InvocationTargetException ex) {
            return List.of();
        }
    }

    /**
     * Creates an exercise selection task.
     *
     * @param project the current project
     * @return the task
     */
    @Nonnull
    public static DodonaResultTask<Optional<FullIdentification>> create(final Project project) {
        return create(project, DodonaBundle.message("tasks.identify.title.default"));
    }

    /**
     * Creates an exercise selection task.
     *
     * @param project the current project
     * @param title   custom title
     * @return the task
     */
    @Nonnull
    public static DodonaResultTask<Optional<FullIdentification>> create(
            final Project project, @Nls final String title) {
        final DodonaResultTask<List<FullIdentification>> task = new IdentifyTask(project, title, false);
        return () -> task.execute().stream().findFirst();
    }

    /**
     * Creates an exercise selection task that allows selecting multiple
     * exercises.
     *
     * @param project the current project
     * @return the task, resulting in an empty list if cancelled
     */
    @Nonnull
    public static DodonaResultTask<List<FullIdentification>> createMultiple(final Project project) {
        return new IdentifyTask(project, DodonaBundle.message("tasks.identify.title.default"), true);
    }

    /**
     * Shows the selection dialog.
     */
    private void showDialog() {
        final IdentifyExerciseDialog dialog = new IdentifyExerciseDialog(this.myProject, this.executor, this.multiple);

        // Show the dialog.
        if (dialog.showAndGet()) {
            this.identifications = dialog.getSelectedExercises();
        }
    }
}
