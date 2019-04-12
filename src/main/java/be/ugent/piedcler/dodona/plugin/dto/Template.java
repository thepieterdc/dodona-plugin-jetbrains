/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.dto;

import javax.annotation.Nonnull;

/**
 * A template for an exercise.
 */
public class Template {
	private final String contents;
	private final String name;
	
	/**
	 * Template constructor.
	 *
	 * @param name     template display name
	 * @param contents template contents
	 */
	Template(final String name, final String contents) {
		this.contents = contents;
		this.name = name;
	}
	
	/**
	 * Gets the contents.
	 *
	 * @return the contents
	 */
	@Nonnull
	public String getContents() {
		return this.contents;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Nonnull
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return String.format("Template{name=%s}", this.name);
	}
}
