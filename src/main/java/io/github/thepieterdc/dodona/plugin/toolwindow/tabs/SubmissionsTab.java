/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.tabs;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBusConnection;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.exercise.CurrentExerciseListener;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.exercise.identification.IdentificationService;
import io.github.thepieterdc.dodona.plugin.toolwindow.ui.submissions.SubmissionsPanel;
import io.github.thepieterdc.dodona.plugin.ui.panels.NoFileOpenedPanel;
import io.github.thepieterdc.dodona.plugin.ui.panels.UnknownExercisePanel;
import io.github.thepieterdc.dodona.plugin.ui.util.PanelUtils;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Controller for the tab showing previous submissions.
 */
public class SubmissionsTab extends AbstractTab {
	private static final String TAB_TITLE = DodonaBundle.message("toolwindow.submissions.title");
	
	@NonNls
	private static final String CARD_NO_FILE = "SUBMISSIONS_NO_FILE";
	@NonNls
	private static final String CARD_UNKNOWN = "SUBMISSIONS_UNKNOWN";
	
	private final Map<String, SubmissionsPanel> cards;
	
	private final JPanel panel;
	
	private final DodonaExecutorHolder executor;
	private final IdentificationService identificationService;
	private final Project project;
	
	/**
	 * SubmissionsTab constructor.
	 *
	 * @param project  current active project
	 * @param executor request executor
	 */
	public SubmissionsTab(final Project project,
	                      final DodonaExecutorHolder executor) {
		super(TAB_TITLE);
		this.cards = new HashMap<>(5);
		this.executor = executor;
		this.identificationService = IdentificationService.getInstance();
		this.panel = new JPanel(new CardLayout(0, 0));
		this.project = project;
		this.initialize();
	}
	
	@Nonnull
	@Override
	JComponent createContent() {
		return this.panel;
	}
	
	/**
	 * Shows the correct card.
	 *
	 * @param file           the currently opened file
	 * @param identification the identification of the file
	 */
	private void handle(@Nullable final VirtualFile file,
	                    @Nullable final Identification identification) {
		if (file == null) {
			// Show the no-file card.
			PanelUtils.showCard(this.panel, CARD_NO_FILE);
			return;
		} else if (identification == null) {
			// Show the unknown exercise card.
			PanelUtils.showCard(this.panel, CARD_UNKNOWN);
			return;
		}
		
		final String cardName = identification.toString();
		
		// Attempt to recycle a previous card.
		if (!this.cards.containsKey(cardName)) {
			// Create a new card.
			final SubmissionsPanel card = new SubmissionsPanel(
				this.project, this.executor, identification
			);
			
			this.cards.put(cardName, card);
			this.panel.add(card, cardName);
		}
		
		// Get the card and refresh the list of submissions.
		final SubmissionsPanel card = this.cards.get(cardName);
		card.requestUpdate();
		
		PanelUtils.showCard(this.panel, cardName);
	}
	
	/**
	 * Initializes the component.
	 */
	private void initialize() {
		// Add the no-file card.
		this.panel.add(new NoFileOpenedPanel().wrap(), CARD_NO_FILE);
		
		// Add the unknown exercise card.
		this.panel.add(new UnknownExercisePanel(this.project).wrap(), CARD_UNKNOWN);
		
		// Listen for changes in opened exercises.
		final MessageBusConnection conn = this.project.getMessageBus().connect();
		conn.subscribe(CurrentExerciseListener.CHANGED_TOPIC, this::handle);
		
		// Handle the current opened exercise.
		final Optional<VirtualFile> vfile = Optional
			.ofNullable(FileEditorManager.getInstance(this.project))
			.map(FileEditorManager::getSelectedEditor)
			.map(FileEditor::getFile);
		final Optional<Identification> identification = vfile.flatMap(file ->
			this.identificationService.identify(this.project, file)
		);
		
		this.handle(vfile.orElse(null), identification.orElse(null));
	}
}
