/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Module type for Dodona projects.
 *
 * @param <B> builder class
 */
public abstract class DodonaModuleType<B extends ModuleBuilder> extends ModuleType<B> {
	private final String description;
	private final String name;
	
	/**
	 * DodonaModuleType constructor.
	 *
	 * @param id          the id of the module type
	 * @param name        the name of the module
	 * @param description the description of the module
	 */
	protected DodonaModuleType(@NonNls final String id, final String name, final String description) {
		super(id);
		this.description = description;
		this.name = name;
	}
	
	@NotNull
	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public Icon getNodeIcon(final boolean b) {
		return Icons.DODONA;
	}
	
	@NotNull
	@Override
	public String getName() {
		return this.name;
	}
}
