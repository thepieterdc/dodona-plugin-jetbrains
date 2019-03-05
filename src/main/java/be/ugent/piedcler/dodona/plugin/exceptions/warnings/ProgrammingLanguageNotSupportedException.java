/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.exceptions.warnings;

import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;
import be.ugent.piedcler.dodona.resources.ProgrammingLanguage;

import javax.annotation.Nonnull;

/**
 * The programming language was not supported by the plugin.
 */
public final class ProgrammingLanguageNotSupportedException extends WarningMessageException {
	private static final long serialVersionUID = 7625672228052177914L;
	
	private final ProgrammingLanguage language;
	
	/**
	 * ProgrammingLanguageNotSupportedException constructor.
	 *
	 * @param language the programming language
	 */
	public ProgrammingLanguageNotSupportedException(@Nonnull final ProgrammingLanguage language) {
		super("The programming language \"%s\" is not currently supported by the plugin. Please report this at the repository so it can be added.");
		this.language = language;
	}
	
	/**
	 * Gets the programming language.
	 *
	 * @return the programming language
	 */
	@Nonnull
	public ProgrammingLanguage getLanguage() {
		return this.language;
	}
}
