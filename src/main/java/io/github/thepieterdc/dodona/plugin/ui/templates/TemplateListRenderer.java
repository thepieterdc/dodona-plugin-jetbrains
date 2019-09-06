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
import com.intellij.ui.ColoredListCellRenderer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Renders the template name correctly in a list of templates.
 */
public class TemplateListRenderer extends ColoredListCellRenderer<Template> {
	@Override
	protected void customizeCellRenderer(@NotNull JList<? extends Template> list,
	                                     @Nonnull final Template template,
	                                     int index,
	                                     boolean selected,
	                                     boolean hasFocus) {
		this.append(template.getName());
	}
}
