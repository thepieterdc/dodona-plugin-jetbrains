/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.code.DodonaFileType;
import io.github.thepieterdc.dodona.plugin.ui.AsyncContentPanel;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.Submission;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Panel that displays the code of a submission.
 */
final class SubmissionCodeEditor extends JPanel implements Disposable {
	@NonNls
	private static final String CARD_EDITOR = "CODE_EDITOR";
	@NonNls
	private static final String CARD_LOADING = "CODE_LOADING";
	
	private final Project project;
	
	private final EditorFactory editorFactory;
	
	@Nullable
	private Editor editor;
	
	/**
	 * SubmissionDetails constructor.
	 *
	 * @param project the current active project
	 */
	SubmissionCodeEditor(final Project project) {
		super(new CardLayout(0, 0));
		this.editorFactory = EditorFactory.getInstance();
		this.project = project;
		this.initialize();
	}
	
	@Override
	public void dispose() {
		if (this.editor != null) {
			this.editorFactory.releaseEditor(this.editor);
		}
	}
	
	/**
	 * Initializes the components.
	 */
	private void initialize() {
		// Create a loading card.
		final JScrollPane loadingCard = AsyncContentPanel.createLoadingCard(
			this,
			this.getClass(),
			"dialog.submission_details.loading"
		);
		
		// Add the loading card.
		this.add(loadingCard, CARD_LOADING);
	}
	
	/**
	 * Shows the given submission in the card.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	void submissionLoaded(final Exercise exercise,
	                      final Submission submission) {
		final Document document = this.editorFactory
			.createDocument(submission.getCode());
		document.setReadOnly(true);
		
		final FileType fileType = exercise.getProgrammingLanguage()
			.flatMap(DodonaFileType::find)
			.orElse(UnknownFileType.INSTANCE);
		
		SwingUtilities.invokeLater(() -> {
			this.editor = this.editorFactory.createEditor(
				document, this.project, fileType, true
			);
			
			final JPanel editorCard = new JPanel(new BorderLayout());
			editorCard.add(Objects.requireNonNull(this.editor).getComponent(), BorderLayout.CENTER);
			
			this.add(editorCard, CARD_EDITOR);
			
			AsyncContentPanel.showCard(this, CARD_EDITOR);
		});
	}
}
