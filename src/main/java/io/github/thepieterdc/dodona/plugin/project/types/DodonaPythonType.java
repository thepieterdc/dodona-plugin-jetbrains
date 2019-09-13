/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project.types;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.project.DodonaModuleType;
import io.github.thepieterdc.dodona.plugin.project.builders.DodonaPythonBuilder;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Python implementation of a DodonaModuleType.
 */
public class DodonaPythonType extends DodonaModuleType<DodonaPythonBuilder> {
	private static final DodonaPythonType INSTANCE = new DodonaPythonType();
	
	public static final String MODULE_TYPE_DESCRIPTION = DodonaBundle.message("module.python.description");
	
	@NonNls
	private static final String MODULE_TYPE_ID = "DODONA_PYTHON_MODULE";
	
	public static final String MODULE_TYPE_NAME = DodonaBundle.message("module.python.name");
	
	/**
	 * DodonaPythonType constructor.
	 */
	public DodonaPythonType() {
		super(DodonaPythonType.MODULE_TYPE_ID, DodonaPythonType.MODULE_TYPE_NAME, DodonaPythonType.MODULE_TYPE_DESCRIPTION);
	}
	
	@NotNull
	@Override
	public DodonaPythonBuilder createModuleBuilder() {
		return new DodonaPythonBuilder();
	}
	
	/**
	 * Gets the type instance.
	 *
	 * @return instance of the module type
	 */
	@Nonnull
	public static DodonaPythonType getInstance() {
		return DodonaPythonType.INSTANCE;
	}
}
