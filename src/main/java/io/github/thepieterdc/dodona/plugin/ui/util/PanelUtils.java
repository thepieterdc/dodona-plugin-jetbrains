/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.util;

import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.ui.AsyncProcessIcon;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * Utilities to create panels.
 */
public final class PanelUtils {
	/**
	 * PanelUtils constructor.
	 */
	private PanelUtils() {
		// Utility class.
	}
	
	/**
	 * Wraps the inner panel in a scroll pane.
	 *
	 * @param inner the inner panel to wrap
	 * @return the panel
	 */
	@Nonnull
	private static JScrollPane create(final JPanel inner) {
		final JPanel panel = new JPanel(new GridBagLayout());
		panel.add(inner, new GridBagConstraints());
		return ScrollPaneFactory.createScrollPane(panel, true);
	}
	
	/**
	 * Creates a loading panel.
	 *
	 * @param parent         the parent component
	 * @param parentClass    the class of the parent
	 * @param loadingTextKey the key to the loading text to display underneath
	 *                       the spinner
	 * @return the created panel
	 */
	@Nonnull
	public static JScrollPane createLoading(final JPanel parent,
	                                        final Class<?> parentClass,
	                                        @PropertyKey(resourceBundle = DodonaBundle.BUNDLE_NAME) final String loadingTextKey) {
		final AsyncProcessIcon loadingIcon = new AsyncProcessIcon.Big(parentClass + ".loading");
		loadingIcon.setBackground(parent.getBackground());
		
		final JPanel loadingPanel = new JPanel(new BorderLayout(10, 10));
		loadingPanel.add(
			new JLabel(DodonaBundle.message(loadingTextKey)),
			BorderLayout.PAGE_END
		);
		loadingPanel.add(loadingIcon, BorderLayout.CENTER);
		
		return create(loadingPanel);
	}
	
	/**
	 * Shows the card with the given name in the parent panel.
	 *
	 * @param parent the container to show the card in
	 * @param card   the card to show
	 */
	public static void showCard(final JPanel parent,
	                            @NonNls final String card) {
		((CardLayout) parent.getLayout()).show(parent, card);
	}
}
