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
	 * @param courseId        the active course
	 * @param futureDeadlines the upcoming deadlines
	 */
	public DeadlinesPanel(final long courseId,
	                      final CompletionStage<? extends List<Deadline>> futureDeadlines) {
		super(new DeadlinesList(courseId), "toolwindow.deadlines.loading");
		this.setBorder(BorderFactory.createEmptyBorder());
		
		futureDeadlines.whenComplete((deadlines, error) -> {
			this.content.setDeadlines(deadlines);
			this.showContentCard();
		});
	}
}
