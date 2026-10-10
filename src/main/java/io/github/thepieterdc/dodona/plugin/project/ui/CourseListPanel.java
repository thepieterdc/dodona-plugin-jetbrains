/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.project.ui;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.ui.listeners.ItemSelectedListener;
import io.github.thepieterdc.dodona.plugin.ui.panels.async.StaticAsyncPanel;
import io.github.thepieterdc.dodona.plugin.ui.resources.course.TabbedCourseList;
import io.github.thepieterdc.dodona.resources.Course;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * Panel that displays the list of courses.
 */
public class CourseListPanel extends StaticAsyncPanel<List<Course>, TabbedCourseList> {
    private final TabbedCourseList coursesList;

    private final DodonaExecutorHolder executor;

    /**
     * CourseListPanel constructor.
     *
     * @param executor request executor
     */
    public CourseListPanel(final DodonaExecutorHolder executor) {
        super(null, DodonaBundle.message("module.course.loading"));
        this.coursesList = new TabbedCourseList();
        this.executor = executor;
    }

    @Nonnull
    @Override
    protected TabbedCourseList createContentPane() {
        return this.coursesList;
    }

    @Nonnull
    @Override
    protected CompletableFuture<List<Course>> getData() {
        return this.executor.getExecutor().execute(dodona -> dodona.me().getSubscribedCourses());
    }

    /**
     * Registers a listener that is notified when the selected course changes.
     *
     * @param listener the listener to register
     */
    public void addSelectionListener(final ItemSelectedListener<Course> listener) {
        this.coursesList.addSelectionListener(listener);
    }

    /**
     * Gets the current selected course, if any.
     *
     * @return the current selected course
     */
    @Nonnull
    public Optional<Course> getSelectedCourse() {
        return this.coursesList.getSelectedCourse();
    }
}
