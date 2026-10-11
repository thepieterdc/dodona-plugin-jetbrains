/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui.identification;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Disposer;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Series;
import io.github.thepieterdc.dodona.resources.activities.Exercise;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

/**
 * Controller dialog that prompts the user to identify an exercise.
 */
public final class IdentifyExerciseDialog extends DialogWrapper implements IdentificationListener {
    private static final int HEIGHT = 250;
    private static final int HEIGHT_MULTIPLE = 280;
    private static final int WIDTH = 400;

    @Nullable
    private Course selectedCourse;

    private List<? extends Exercise> selectedExercises = List.of();

    @Nullable
    private Series selectedSeries;

    private final IdentifyExercisePanel root;

    /**
     * IdentifyExerciseDialog constructor.
     *
     * @param project  the current project
     * @param executor request executor
     */
    public IdentifyExerciseDialog(final Project project, final DodonaExecutorHolder executor) {
        this(project, executor, false);
    }

    /**
     * IdentifyExerciseDialog constructor.
     *
     * @param project  the current project
     * @param executor request executor
     * @param multiple whether multiple exercises can be selected
     */
    public IdentifyExerciseDialog(final Project project, final DodonaExecutorHolder executor, final boolean multiple) {
        super(project, true);
        this.root = new IdentifyExercisePanel(project, executor, this, this::doOKAction, multiple);
        this.root.setPreferredSize(new Dimension(WIDTH, multiple ? HEIGHT_MULTIPLE : HEIGHT));
        this.setTitle(DodonaBundle.message("dialog.select_exercise.title"));
        this.verifyOKAction();
        this.init();
    }

    @Override
    protected JComponent createCenterPanel() {
        return this.root;
    }

    @Override
    protected void dispose() {
        Disposer.dispose(this.root);
        super.dispose();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return this.getButton(this.getCancelAction());
    }

    /**
     * Gets the selected exercises.
     *
     * @return the identified exercises, empty if nothing is selected
     */
    @Nonnull
    public List<FullIdentification> getSelectedExercises() {
        if (this.selectedCourse == null || this.selectedSeries == null) {
            return List.of();
        }
        return this.selectedExercises.stream()
                .map(exercise -> new FullIdentification(this.selectedCourse, this.selectedSeries, exercise))
                .collect(Collectors.toList());
    }

    @Override
    public void onCourseSelected(@Nullable final Course selected) {
        this.selectedCourse = selected;
        this.verifyOKAction();
    }

    @Override
    public void onExercisesSelected(final List<? extends Exercise> selected) {
        this.selectedExercises = selected;
        this.verifyOKAction();
    }

    @Override
    public void onSeriesSelected(@Nullable final Series selected) {
        this.selectedSeries = selected;
        this.verifyOKAction();
    }

    /**
     * Sets the OK action button state.
     */
    private void verifyOKAction() {
        this.setOKActionEnabled(
                this.selectedCourse != null && !this.selectedExercises.isEmpty() && this.selectedSeries != null);
    }
}
