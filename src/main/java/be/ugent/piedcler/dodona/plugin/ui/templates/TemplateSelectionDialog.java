/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.templates;

import be.ugent.piedcler.dodona.plugin.templates.Template;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

/**
 * A dialog that asks the user to select a template for an exercise.
 */
public class TemplateSelectionDialog extends DialogWrapper {
	private Editor editor;
	
	private final Project project;
	
	private final List<Template> templates;
	
	/**
	 * TemplateSelectionDialog constructor.
	 *
	 * @param templates the templates to select from
	 */
	public TemplateSelectionDialog(final Project project, final List<Template> templates) {
		super(project, false);
		this.project = project;
		this.templates = templates;
		
		this.setCancelButtonText("Create empty");
		this.setOKButtonText("Create from template");
		this.setTitle("Select Template");
		this.init();
	}
	
	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		final ComboBoxModel<Template> templateSelectModel = new DefaultComboBoxModel<>(new Vector<>(this.templates));
		final ComboBox<Template> templateSelect = new ComboBox<>(templateSelectModel);
		templateSelect.addItemListener(e -> {
			final Template selected = templateSelectModel.getElementAt(templateSelect.getSelectedIndex());
			this.editor.getDocument().setText(selected.getContents());
		});
		templateSelect.setRenderer(new TemplateListRenderer());
		templateSelect.setMinimumAndPreferredWidth(700);
		
		this.editor = this.createEditor();
		this.editor.getDocument().setText(this.templates.get(0).getContents());
		
		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(templateSelect);
		centerPanel.add(this.editor.getComponent());
		
		final JPanel root = new JPanel(new BorderLayout());
		root.setPreferredSize(new Dimension(700, 400));
		root.add(centerPanel, BorderLayout.CENTER);
		
		return root;
	}
	
	/**
	 * Creates an editor panel.
	 *
	 * @return the editor panel
	 */
	@Nonnull
	private Editor createEditor() {
		final Document document = EditorFactory.getInstance().createDocument("");
		return EditorFactory.getInstance().createEditor(document, this.project);
	}
	
	@Override
	public void dispose() {
		EditorFactory.getInstance().releaseEditor(this.editor);
		super.dispose();
	}
	
	public Optional<Template> getTemplate() {
		return Optional.of(Template.custom(this.editor.getDocument().getText()));
	}
}
