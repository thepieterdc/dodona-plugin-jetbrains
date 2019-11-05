/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines;

import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.ui.AsyncProcessIcon;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.DeadlinesTab;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * UI for the Deadlines tab.
 */
public final class DeadlinesPanel extends JPanel {
	@NonNls
	private static final String DEADLINES_CARD_DEADLINES = "DEADLINES_DEADLINES";
	@NonNls
	private static final String DEADLINES_CARD_LOADING = "DEADLINES_LOADING";
	
	private final DeadlinesList deadlinesList;
	
	private final AsyncProcessIcon loadingIcon;
	
	/**
	 * DeadlinesPanel constructor.
	 */
	public DeadlinesPanel(final long courseId) {
		super(new CardLayout(0, 0));
		this.deadlinesList = new DeadlinesList(courseId);
		this.loadingIcon = new AsyncProcessIcon.Big(this.getClass() + ".loading");
		this.setBorder(BorderFactory.createEmptyBorder());
		this.initialize();
	}
	
	private void initialize() {
		this.add(
			ScrollPaneFactory.createScrollPane(this.deadlinesList, true),
			DEADLINES_CARD_DEADLINES
		);
		
		final JPanel loadingInnerPanel = new JPanel(new BorderLayout(10, 10));
		loadingInnerPanel.add(
			new JLabel(DodonaBundle.message("toolwindow.deadlines.loading")),
			BorderLayout.PAGE_END
		);
		loadingInnerPanel.add(this.loadingIcon, BorderLayout.CENTER);
		
		final JPanel loadingPanel = new JPanel(new GridBagLayout());
		loadingPanel.add(loadingInnerPanel, new GridBagConstraints());
		
		this.add(
			ScrollPaneFactory.createScrollPane(loadingPanel, true),
			DEADLINES_CARD_LOADING
		);
		
		// Show the loading card.
		this.showLoading();
	}
	
	/**
	 * Shows the deadlines card.
	 */
	public void showDeadlines() {
		this.loadingIcon.setBackground(this.deadlinesList.getBackground());
		((CardLayout) this.getLayout()).show(
			this,
			DEADLINES_CARD_DEADLINES
		);
	}
	
	/**
	 * Shows the loading card.
	 */
	public void showLoading() {
		this.loadingIcon.setBackground(this.deadlinesList.getBackground());
		((CardLayout) this.getLayout()).show(
			this,
			DEADLINES_CARD_LOADING
		);
	}
	
	/**
	 * Updates the list of deadlines.
	 *
	 * @param deadlines the new list of deadlines
	 */
	public void update(final List<? extends DeadlinesTab.Deadline> deadlines) {
		this.deadlinesList.setDeadlines(deadlines);
	}
}
