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
import io.github.thepieterdc.dodona.plugin.project.builders.DodonaJavaBuilder;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Java implementation of a DodonaModuleType.
 */
public class DodonaJavaType extends DodonaModuleType<DodonaJavaBuilder> {
	private static final DodonaJavaType INSTANCE = new DodonaJavaType();
	
	public static final String MODULE_TYPE_DESCRIPTION = DodonaBundle.message("module.java.description");
	
	@NonNls
	private static final String MODULE_TYPE_ID = "DODONA_JAVA_MODULE";
	
	public static final String MODULE_TYPE_NAME = DodonaBundle.message("module.java.name");
	
	/**
	 * DodonaJavaType constructor.
	 */
	public DodonaJavaType() {
		super(MODULE_TYPE_ID, MODULE_TYPE_NAME, MODULE_TYPE_DESCRIPTION);
	}
	
	@NotNull
	@Override
	public DodonaJavaBuilder createModuleBuilder() {
		return new DodonaJavaBuilder();
	}
	
	/**
	 * Gets the type instance.
	 *
	 * @return instance of the module type
	 */
	@Nonnull
	public static DodonaJavaType getInstance() {
		return INSTANCE;
	}
}
