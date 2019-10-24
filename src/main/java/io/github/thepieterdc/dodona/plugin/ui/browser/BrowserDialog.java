/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.browser;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.ui.AsyncProcessIcon;
import io.github.thepieterdc.dodona.plugin.authentication.AuthenticationConsumer;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Dialog showing a webbrowser.
 */
public class BrowserDialog extends DialogWrapper implements AuthenticationConsumer {
	@NonNls
	private static final String CARD_LOADING = "BROWSER_LOADING";
	@NonNls
	private static final String CARD_PAGE = "BROWSER_PAGE";
	
	@NonNls
	private static final String CONTENT_TYPE = "text/html";
	
	private static final int HEIGHT = 250;
	private static final int WIDTH = 400;
	
	private String token;
	
	private final AsyncProcessIcon loadingIcon;
	
	private final JEditorPane browserPanel;
	private JPanel rootPanel;
	
	/**
	 * BrowserDialog constructor.
	 *
	 * @param project the current project
	 */
	public BrowserDialog(final Project project,
	                     final String title) {
		super(project, true);
		this.browserPanel = new JEditorPane();
		this.loadingIcon = new AsyncProcessIcon(this.getClass() + ".loading");
		this.rootPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		this.setTitle(title);
		this.init();
		
		this.initialize();
	}
	
	@Override
	public void authenticate(final String nwToken) {
		this.token = nwToken;
	}
	
	@Override
	protected JComponent createCenterPanel() {
		return this.rootPanel;
	}
	
	/**
	 * Initializes the UI.
	 */
	private void initialize() {
		// Configure the browser panel.
		this.browserPanel.setContentType(CONTENT_TYPE);
		this.browserPanel.setEditable(false);
		
		// Add the browser panel.
		this.rootPanel.add(
			ScrollPaneFactory.createScrollPane(this.browserPanel),
			CARD_PAGE
		);
		
		// Add the loading panel.
		final JPanel loadingPanel = new JPanel(new BorderLayout());
		loadingPanel.add(this.loadingIcon, BorderLayout.CENTER);
		this.rootPanel.add(loadingPanel, CARD_LOADING);
	}
	
	/**
	 * Loads the given url in the browser window.
	 *
	 * @param url the url to load
	 */
	public void loadUrl(final String url) {
		try {
			this.browserPanel.setPage(url);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
