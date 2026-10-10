package io.github.thepieterdc.dodona.plugin.project;

import com.intellij.configurationStore.StoreUtil;
import com.intellij.facet.ui.ValidationResult;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.DirectoryProjectGeneratorBase;
import com.intellij.platform.ProjectGeneratorPeer;
import com.intellij.platform.WebProjectGenerator;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.project.ui.CourseListPanel;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.resources.Course;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;

/**
 * Project generator for PyCharm.
 */
public final class DodonaPythonProjectGenerator extends DirectoryProjectGeneratorBase<Course> {
    @Override
    public @NotNull String getName() {
        return DodonaBundle.message("module.python.name");
    }

    @Override
    public String getDescription() {
        return DodonaBundle.message("module.python.description");
    }

    @Override
    public @NotNull Icon getLogo() {
        return Icons.DODONA;
    }

    @Override
    public @NotNull ProjectGeneratorPeer<Course> createPeer() {
        return new CoursePeer();
    }

    @Override
    public void generateProject(
            @NotNull final Project project,
            @NotNull final VirtualFile baseDir,
            @NotNull final Course course,
            @NotNull final Module module) {
        DodonaProjectSettings.getInstance(project).setCourseId(course.getId());
        StoreUtil.saveSettings(project);
    }

    @Override
    public @NotNull ValidationResult validate(@NotNull final String baseDirPath) {
        return ValidationResult.OK;
    }

    /**
     * Lets the user select a course.
     */
    private static final class CoursePeer implements ProjectGeneratorPeer<Course> {
        private final CourseListPanel panel =
                new CourseListPanel(DodonaAuthenticator.getInstance().getExecutor());

        private final List<ProjectGeneratorPeer.SettingsListener> listeners = new ArrayList<>(2);
        private final List<WebProjectGenerator.SettingsStateListener> stateListeners = new ArrayList<>(2);

        private CoursePeer() {
            this.panel.addSelectionListener(course -> {
                // Let the wizard validate again, so that the create button gets enabled.
                this.listeners.forEach(l -> l.stateChanged(course != null));
                this.stateListeners.forEach(l -> l.stateChanged(course != null));
            });
            this.panel.requestUpdate();
        }

        @Override
        public void addSettingsListener(@NotNull final ProjectGeneratorPeer.SettingsListener listener) {
            this.listeners.add(listener);
        }

        @Override
        public void addSettingsStateListener(@NotNull final WebProjectGenerator.SettingsStateListener listener) {
            this.stateListeners.add(listener);
        }

        @Override
        public @NotNull JComponent getComponent() {
            return this.panel;
        }

        @Override
        public @NotNull JComponent getComponent(
                @NotNull final TextFieldWithBrowseButton locationField, @NotNull final Runnable checkValid) {
            this.panel.addSelectionListener(course -> checkValid.run());
            return this.panel;
        }

        @Override
        public void buildUI(@NotNull final SettingsStep settingsStep) {
            settingsStep.addSettingsComponent(this.panel);
        }

        @Override
        public @NotNull Course getSettings() {
            return this.panel.getSelectedCourse().orElseThrow();
        }

        @Override
        public ValidationInfo validate() {
            if (this.panel.getSelectedCourse().isEmpty()) {
                return new ValidationInfo(DodonaBundle.message("module.course.blank"), this.panel);
            }
            return null;
        }

        @Override
        public boolean isBackgroundJobRunning() {
            return false;
        }
    }
}
