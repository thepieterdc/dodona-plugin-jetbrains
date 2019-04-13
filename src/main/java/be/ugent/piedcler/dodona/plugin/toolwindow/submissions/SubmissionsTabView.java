/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindow.submissions;

import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Controller for the tab showing exercise submissions.
 */
public class SubmissionsTabView {
	/**
	 * Listener for newly opened/closed files.
	 */
	private static class FileChangedListener implements FileEditorManagerListener {
		private final Consumer<VirtualFile> handler;
		
		/**
		 * FileChangedListener constructor.
		 *
		 * @param handler the handler
		 */
		FileChangedListener(@Nonnull final Consumer<VirtualFile> handler) {
			this.handler = handler;
		}
		
		@Override
		public void selectionChanged(@NotNull final FileEditorManagerEvent event) {
			this.handler.accept(event.getNewFile());
		}
	}
	
	private final Content content;
	
	private final SubmissionsTabPanel panel;
	
	private final Project project;
	
	/**
	 * SubmissionsTabView constructor.
	 *
	 * @param project the project
	 */
	public SubmissionsTabView(@Nonnull final Project project) {
		this.panel = new SubmissionsTabPanel();
		this.project = project;
		
		this.content = ContentFactory.SERVICE.getInstance().createContent(this.panel, "Submissions", false);
		this.content.setCloseable(false);
		
		project.getMessageBus()
			.connect(this.content)
			.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileChangedListener(this::loadFile));
	}
	
	/**
	 * Gets the content pane.
	 *
	 * @return content pane
	 */
	public Content getContent() {
		return this.content;
	}
	
	/**
	 * Loads a file into the view.
	 *
	 * @param file the file to load
	 */
	public void loadFile(@Nonnull final VirtualFile file) {
		System.out.println(file);
	}
}
