/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindows;

import be.ugent.piedcler.dodona.resources.submissions.PartialSubmission;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Creates tool windows to list submissions.
 */
public class SubmissionsToolWindowFactory implements ToolWindowFactory, DumbAware {
	@Override
	public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
		final JPanel root = new JPanel(new BorderLayout());
		
		final DefaultListModel<PartialSubmission> submissionListModel = new DefaultListModel<>();
		final JBList<PartialSubmission> submissionList = new JBList<>(submissionListModel);
		
		root.add(submissionList, BorderLayout.CENTER);
		
		toolWindow.setTitle("Submissions");
		toolWindow.getContentManager().addContent(ContentFactory.SERVICE.getInstance().createContent(root, "Submissions", true));
	}
}
