/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindow;

import io.github.thepieterdc.dodona.plugin.toolwindow.tabs.SubmissionsTab;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;

import javax.annotation.Nonnull;

/**
 * Tool window containing last submissions for the current exercise.
 */
public class DodonaToolWindowInitialiser {
	private final Project project;
	private ToolWindow toolWindow;
	
	/**
	 * DodonaToolWindowView constructor.
	 *
	 * @param project current active project
	 */
	public DodonaToolWindowInitialiser(@Nonnull final Project project) {
		this.project = project;
	}
	
	/**
	 * Creates a new tool window view.
	 *
	 * @param project current active project
	 * @return instance
	 */
	public static be.ugent.piedcler.dodona.plugin.toolwindow.DodonaToolWindowInitialiser getInstance(@Nonnull final Project project) {
		return project.getComponent(be.ugent.piedcler.dodona.plugin.toolwindow.DodonaToolWindowInitialiser.class);
	}
	
	void initToolWindow(@Nonnull final ToolWindow toolWindow) {
		if (this.toolWindow != null) {
			return;
		}
		
		final SubmissionsTab submissionsTab = new SubmissionsTab(this.project);
		
		this.toolWindow = toolWindow;
		this.toolWindow.getContentManager().addContent(submissionsTab.getContent());
		this.toolWindow.setToHideOnEmptyContent(true);
		
		final VirtualFile[] files = FileEditorManager.getInstance(this.project).getSelectedFiles();
		submissionsTab.loadFile(files.length == 0 ? null : files[0]);
	}
}
