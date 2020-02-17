/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines;

import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.ui.Deadline;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;
import io.github.thepieterdc.dodona.plugin.ui.cards.IconTextCard;
import io.github.thepieterdc.dodona.plugin.ui.panels.async.StaticAsyncPanel;
import io.github.thepieterdc.dodona.resources.Course;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * UI for the Deadlines tab.
 */
public final class DeadlinesPanel extends StaticAsyncPanel<List<Deadline>, DeadlinesList> {
	@NonNls
	private static final String CARD_NONE = "DEADLINES_NONE";
	
	private static final JComponent ICON_NONE = Icons.toComponent(
		Icons.DEADLINES_CHECK.color(TextColors.SECONDARY)
	);
	
	private final DeadlinesList list;
	
	private final DodonaExecutorHolder executor;
	
	/**
	 * DeadlinesPanel constructor.
	 *
	 * @param project  current active project
	 * @param executor request executor holder
	 */
	public DeadlinesPanel(final Project project,
	                      final DodonaExecutorHolder executor) {
		super(project, DodonaBundle.message("toolwindow.deadlines.loading"));
		this.executor = executor;
		this.list = new DeadlinesList();
		this.setBorder(BorderFactory.createEmptyBorder());
	}
	
	@Nonnull
	@Override
	protected DeadlinesList createContentPane() {
		return this.list;
	}
	
	@Nonnull
	@Override
	protected CompletableFuture<List<Deadline>> getData() {
		return this.executor.getExecutor().execute(DodonaClient::root).thenApply(root -> {
			// Create a map of the course ids to their names.
			final Map<Long, String> courseNames = root.getUser()
				.getSubscribedCourses()
				.stream()
				.collect(Collectors.toMap(Course::getId, Course::getName));
			
			// Create a deadline object for every deadline.
			return root.getDeadlineSeries().stream()
				.map(series -> Deadline.parse(courseNames, series))
				.sorted()
				.collect(Collectors.toList());
		});
	}
	
	@Override
	protected void initialize(@Nls final String loadingText) {
		super.initialize(loadingText);
		
		// Create a card that is shown when no deadlines were found.
		this.add(
			CARD_NONE,
			new IconTextCard(ICON_NONE, DodonaBundle.message("toolwindow.deadlines.none")).wrap()
		);
	}
	
	@Override
	protected void showContentCard() {
		if (this.list.listSize() > 0) {
			super.showContentCard();
		} else {
			this.showCard(CARD_NONE);
		}
	}
}
