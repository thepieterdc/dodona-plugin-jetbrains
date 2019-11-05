/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api;

import io.github.thepieterdc.dodona.plugin.ui.Icons;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Arrays;

/**
 * Dodona servers that are available with the plugin.
 */
public enum DodonaServer {
	DEFAULT("dodona.ugent.be", "https://dodona.ugent.be", Icons.AVATAR_DEFAULT),
	NAOS("naos.ugent.be", "https://naos.ugent.be", Icons.AVATAR_NAOS),
	LOCAL3000("localhost:3000", "http://localhost:3000", Icons.AVATAR_OTHER);
	
	@NonNls
	private final String displayName;
	
	private final Icon icon;
	
	@NonNls
	private final String url;
	
	/**
	 * DodonaServer constructor.
	 *
	 * @param displayName the human readable name
	 * @param url         the url
	 * @param icon        the icon
	 */
	DodonaServer(final String displayName, final String url, final Icon icon) {
		this.displayName = displayName;
		this.icon = icon;
		this.url = url;
	}
	
	/**
	 * Gets the human readable name of the server.
	 *
	 * @return the human readable name
	 */
	@Nonnull
	public String getDisplayName() {
		return this.displayName;
	}
	
	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	@Nonnull
	public Icon getIcon() {
		return this.icon;
	}
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	@Nonnull
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * Parses a url into a DodonaServer and returns the display name.
	 *
	 * @param url the url
	 * @return the display name
	 */
	@Nonnull
	public static String toDisplayName(final String url) {
		return Arrays.stream(DodonaServer.values())
			.filter(ds -> ds.url.equals(url))
			.map(DodonaServer::getDisplayName)
			.findAny().orElse(url);
	}
	
	/**
	 * Parses a url into a DodonaServer and returns the icon.
	 *
	 * @param url the url
	 * @return the display name
	 */
	@Nonnull
	public static Icon toIcon(final String url) {
		return Arrays.stream(DodonaServer.values())
			.filter(ds -> ds.url.equals(url))
			.map(DodonaServer::getIcon)
			.findAny().orElse(Icons.AVATAR_OTHER);
	}
	
	@Override
	public String toString() {
		return this.displayName;
	}
}
