/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise.creation;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * Creates an exercise file.
 */
public interface ExerciseCreationService {
    /**
     * Creates an exercise file.
     *
     * @param directory      the directory in which the file should be created
     * @param identification the exercise identification
     * @return the created file
     */
    VirtualFile create(final PsiDirectory directory, final FullIdentification identification);

    /**
     * Creates an exercise file using the proposed filename, without prompting
     * the user.
     *
     * @param directory      the directory in which the file should be created
     * @param identification the exercise identification
     * @return the created file, empty if a file with the same name already exists
     */
    @Nonnull
    Optional<VirtualFile> createWithoutPrompt(final PsiDirectory directory, final FullIdentification identification);

    /**
     * Gets an instance of the ExerciseCreationService.
     *
     * @param project the active project
     * @return the instance
     */
    @Nonnull
    static ExerciseCreationService getInstance(final Project project) {
        return project.getService(ExerciseCreationService.class);
    }
}
