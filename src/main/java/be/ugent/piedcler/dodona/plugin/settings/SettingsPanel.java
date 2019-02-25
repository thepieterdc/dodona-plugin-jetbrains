/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.settings;

import javax.swing.*;

/**
 * Settings panel to modify plugin settings.
 */
public class SettingsPanel {
	private JPanel mainPane;
	
	/**
	 * SettingsPanel constructor.
	 */
	public SettingsPanel() {
		this.reset();
	}
	
	/**
	 * Saves the settings.
	 */
	public void apply() {
	
	}
	
	/**
	 * Gets the main panel.
	 *
	 * @return the main panel
	 */
	public JComponent getPanel() {
		return this.mainPane;
	}
	
	/**
	 * Gets whether the settings were modified.
	 *
	 * @return true if the settings were modified
	 */
	public boolean isModified() {
		return false;
	}
	
	/**
	 * Resets the state of the component.
	 */
	public void reset() {
	
	}
}