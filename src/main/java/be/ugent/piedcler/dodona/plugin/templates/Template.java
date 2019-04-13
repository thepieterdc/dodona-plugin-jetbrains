/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.templates;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * A template for an exercise.
 */
public final class Template implements Comparable<Template> {
	private final boolean boilerplate;
	private final String contents;
	private final String name;
	
	/**
	 * Template constructor.
	 *
	 * @param name          template display name
	 * @param contents      template contents
	 * @param isBoilerplate true if this is a boilerplate template
	 */
	private Template(final String name, final String contents, final boolean isBoilerplate) {
		this.boilerplate = isBoilerplate;
		this.contents = contents;
		this.name = name;
	}
	
	/**
	 * Gets a boilerplate template from the given contents.
	 *
	 * @param contents the contents
	 * @return the template
	 */
	@Nonnull
	public static Template boilerplate(final String contents) {
		return new Template("Boilerplate", contents, true);
	}
	
	@Override
	public int compareTo(@NotNull final Template o) {
		return this.name.compareToIgnoreCase(o.name);
	}
	
	/**
	 * Gets a template from the given name and contents.
	 *
	 * @param name     the template name
	 * @param contents the contents
	 * @return the template
	 */
	@Nonnull
	public static Template create(final String name, final String contents) {
		return new Template(name, contents, false);
	}
	
	/**
	 * Gets a template from text entered by the user.
	 *
	 * @param contents the template contents
	 * @return the template
	 */
	@Nonnull
	public static Template custom(final String contents) {
		return new Template("Custom", contents, false);
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
	
	/**
	 * Gets whether this template is a boilerplate template.
	 *
	 * @return true if it is a boilerplate template
	 */
	public boolean isBoilerplate() {
		return this.boilerplate;
	}
	
	@Override
	public String toString() {
		return String.format("Template{name=%s}", this.name);
	}
}
