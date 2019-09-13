/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project.builders;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.jetbrains.python.module.PythonModuleBuilder;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.Icons;
import io.github.thepieterdc.dodona.plugin.project.CourseWizardStep;
import io.github.thepieterdc.dodona.plugin.project.DodonaModuleBuilder;
import io.github.thepieterdc.dodona.plugin.project.types.DodonaPythonType;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Builder for Python projects.
 */
public final class DodonaPythonBuilder extends PythonModuleBuilder implements DodonaModuleBuilder {
	@NonNls
	private static final String BUILDER_ID = "dodona-python";
	
	private int courseId;
	
	/**
	 * DodonaJavaBuilder constructor.
	 */
	public DodonaPythonBuilder() {
		super();
		this.courseId = -1;
		this.addListener(this);
	}
	
	@Nonnull
	@Override
	public String getBuilderId() {
		return DodonaPythonBuilder.BUILDER_ID;
	}
	
	@Nonnull
	@Override
	public ModuleWizardStep getCustomOptionsStep(final WizardContext context, final Disposable parentDisposable) {
		return new CourseWizardStep(this);
	}
	
	@Override
	public String getDescription() {
		return DodonaPythonType.MODULE_TYPE_DESCRIPTION;
	}
	
	@Override
	public String getGroupName() {
		return DodonaBundle.NAME;
	}
	
	@Override
	public Icon getNodeIcon() {
		return Icons.DODONA;
	}
	
	@Override
	public String getParentGroup() {
		return DodonaBundle.NAME;
	}
	
	@Override
	public String getPresentableName() {
		return DodonaPythonType.MODULE_TYPE_NAME;
	}
	
	@Override
	public int getWeight() {
		return DodonaModuleBuilder.WEIGHT;
	}
	
	@Override
	public void moduleCreated(@NotNull final Module module) {
		// Find the newly created project.
		final Project project = module.getProject();
		
		// Set the course id.
		DodonaProjectSettings.getInstance(project).setCourseId(this.courseId);
		
		// Save the project.
		project.save();
	}
	
	@Override
	public void setCourse(final int course) {
		this.courseId = course;
	}
}
