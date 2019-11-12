/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines;

import io.github.thepieterdc.dodona.plugin.ui.AsyncContentPanel;
import io.github.thepieterdc.dodona.plugin.ui.Deadline;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * UI for the Deadlines tab.
 */
public final class DeadlinesPanel extends AsyncContentPanel<DeadlinesList> {
	/**
	 * DeadlinesPanel constructor.
	 *
	 * @param futureDeadlines the upcoming deadlines
	 */
	public DeadlinesPanel(final CompletionStage<? extends List<Deadline>> futureDeadlines) {
		super(new DeadlinesList(), "toolwindow.deadlines.loading");
		this.setBorder(BorderFactory.createEmptyBorder());
		
		futureDeadlines.whenComplete((deadlines, error) -> {
			if (error != null) {
				this.showUnauthenticatedCard();
			} else {
				this.content.setDeadlines(deadlines);
				this.showContentCard();
			}
		});
	}
	
	/**
	 * Sets the current course.
	 *
	 * @param course the current course
	 */
	public void setCurrentCourse(@Nullable final Long course) {
		this.content.setCurrentCourse(course);
	}
}
