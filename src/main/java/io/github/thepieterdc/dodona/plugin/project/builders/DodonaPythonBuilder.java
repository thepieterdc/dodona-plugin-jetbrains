/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.project.builders;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.python.community.plugin.java.facet.PythonModuleBuilder;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.project.DodonaModuleBuilder;
import io.github.thepieterdc.dodona.plugin.project.types.DodonaPythonType;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.resources.Course;
import javax.annotation.Nonnull;
import javax.swing.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Builder for Python projects.
 * <p>
 * PythonModuleBuilder is final, so the Python-specific behaviour is delegated to an instance of it.
 */
public final class DodonaPythonBuilder extends ModuleBuilder implements DodonaModuleBuilder {
    @NonNls
    private static final String BUILDER_ID = "dodona-python";

    private final PythonModuleBuilder delegate = new PythonModuleBuilder();

    private Course course;

    /**
     * DodonaJavaBuilder constructor.
     */
    public DodonaPythonBuilder() {
        super();
        this.addListener(this);
    }

    @Override
    public Module commitModule(@NotNull final Project project, final ModifiableModuleModel model) {
        return this.delegate.commitModule(project, model);
    }

    public void addSdkChangedListener(final Runnable listener) {
        this.delegate.addSdkChangedListener(listener);
    }

    public Sdk getSdk() {
        return this.delegate.getSdk();
    }

    public void setSdk(final Sdk sdk) {
        this.delegate.setSdk(sdk);
    }

    @Override
    public boolean isSuitableSdkType(final SdkTypeId sdkType) {
        return this.delegate.isSuitableSdkType(sdkType);
    }

    @Override
    public ModuleType<?> getModuleType() {
        return this.delegate.getModuleType();
    }

    @Override
    public void setupRootModel(@NotNull final ModifiableRootModel model) throws ConfigurationException {
        this.delegate.setupRootModel(model);
    }

    @Nonnull
    @Override
    public String getBuilderId() {
        return BUILDER_ID;
    }

    @Nonnull
    @Override
    public ModuleWizardStep getCustomOptionsStep(final WizardContext context, final Disposable parentDisposable) {
        return BuilderUtils.createCourseSelectionStep(this);
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
        BuilderUtils.finish(module, this.course);
    }

    @Override
    public void setCourse(final Course course) {
        this.course = course;
    }
}
