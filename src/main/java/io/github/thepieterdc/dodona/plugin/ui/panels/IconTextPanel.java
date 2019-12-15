/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels;

import com.intellij.ui.ScrollPaneFactory;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Common panel that displays an icon and a line of text.
 */
public class IconTextPanel extends JPanel {
	/**
	 * IconTextPanel constructor.
	 *
	 * @param icon the icon to display
	 * @param text the text to display underneath the icon
	 */
	public IconTextPanel(final Icon icon, @Nls final String text) {
		this(Icons.toComponent(icon), text, inner -> {
		});
	}
	
	/**
	 * IconTextPanel constructor.
	 *
	 * @param icon the icon to display
	 * @param text the text to display underneath the icon
	 */
	public IconTextPanel(final JComponent icon, @Nls final String text) {
		this(icon, text, inner -> {
		});
	}
	
	/**
	 * IconTextPanel constructor.
	 *
	 * @param icon          the icon to display
	 * @param text          the text to display underneath the icon
	 * @param innerModifier modifies the inner panel
	 */
	IconTextPanel(final JComponent icon, @Nls final String text,
	              final Consumer<? super JPanel> innerModifier) {
		super(new GridBagLayout());
		
		final JPanel inner = new JPanel(new BorderLayout(10, 10));
		inner.add(new JLabel(text), BorderLayout.PAGE_END);
		inner.add(icon, BorderLayout.CENTER);
		innerModifier.accept(inner);
		this.add(inner, new GridBagConstraints());
	}
	
	/**
	 * Wraps the current pane in a ScrollPane.
	 *
	 * @return the wrapped pane
	 */
	@Nonnull
	public JScrollPane wrap() {
		return ScrollPaneFactory.createScrollPane(this, true);
	}
}
