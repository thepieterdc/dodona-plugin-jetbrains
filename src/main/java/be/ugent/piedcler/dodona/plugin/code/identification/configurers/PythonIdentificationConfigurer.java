/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification.configurers;

import javax.annotation.Nonnull;

/**
 * Identification configurer for Python files.
 */
public class PythonIdentificationConfigurer extends AbstractIdentificationConfigurer {
	private static final String EXTENSION = "py";
	
	@Nonnull
	@Override
	public String getFileExtension() {
		return EXTENSION;
	}
	
	@Nonnull
	@Override
	String getIdentificationLine(@Nonnull String identification) {
		return String.format("# %s\n", identification);
	}
}
