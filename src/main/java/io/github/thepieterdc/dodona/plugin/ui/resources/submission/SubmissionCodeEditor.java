/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.code.DodonaFileType;
import io.github.thepieterdc.dodona.plugin.code.SolutionWithLanguage;
import io.github.thepieterdc.dodona.plugin.ui.panels.async.DynamicAsyncPanel;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.ProgrammingLanguage;
import io.github.thepieterdc.dodona.resources.submissions.Submission;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

/**
 * Panel that displays the code of a submission.
 */
public final class SubmissionCodeEditor extends DynamicAsyncPanel<SolutionWithLanguage> {
	
	private final EditorFactory editorFactory;
	private final DodonaExecutorHolder executor;
	
	@Nullable
	private Editor editor;
	
	private final SubmissionInfo submissionInfo;
	
	/**
	 * SubmissionCodeEditor constructor.
	 *
	 * @param project        the current active project
	 * @param executor       the holder for the request executor
	 * @param submissionInfo submission details
	 */
	public SubmissionCodeEditor(final Project project,
	                            final DodonaExecutorHolder executor,
	                            final SubmissionInfo submissionInfo) {
		super(project, DodonaBundle.message("dialog.submission_details.loading"));
		this.editorFactory = EditorFactory.getInstance();
		this.executor = executor;
		this.submissionInfo = submissionInfo;
	}
	
	@Nonnull
	@Override
	protected Component createContentPane(final SolutionWithLanguage submission) {
		final Document document = this.editorFactory
			.createDocument(submission.getCode());
		document.setReadOnly(true);
		
		final FileType fileType = submission.getProgrammingLanguage()
			.map(ProgrammingLanguage::getExtension)
			.flatMap(DodonaFileType::forExtension)
			.orElse(UnknownFileType.INSTANCE);
		
		this.editor = this.editorFactory
			.createEditor(document, this.project, fileType, true);
		return this.editor.getComponent();
	}
	
	@Override
	public void dispose() {
		if (this.editor != null) {
			this.editorFactory.releaseEditor(this.editor);
			this.editor = null;
		}
	}
	
	@Nonnull
	@Override
	protected CompletableFuture<SolutionWithLanguage> getData() {
		// Get the programming language.
		final CompletableFuture<ProgrammingLanguage> language = this.executor
			.getExecutor()
			.execute(dodona -> dodona.exercises().get(this.submissionInfo))
			.thenApply(Exercise::getProgrammingLanguage)
			.thenApply(optLanguage -> optLanguage.orElse(null));
		
		// Get the solution code.
		final CompletableFuture<Submission> futureSubmission = this.executor
			.getExecutor()
			.execute(dodona -> dodona.submissions().get(this.submissionInfo));
		
		return futureSubmission.thenComposeAsync(submission -> language
			.thenApply(lang -> new SolutionWithLanguage(submission, lang))
		);
	}
}
