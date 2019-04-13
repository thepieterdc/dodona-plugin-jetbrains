/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.plugin.Api;
import be.ugent.piedcler.dodona.plugin.identification.Identification;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.submissions.PartialSubmission;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

/**
 * Gets information about an exercise.
 */
public class GetExerciseInfoTask extends Task.WithResult<Exercise, RuntimeException> {
	private final Identification identification;
	
	/**
	 * GetExerciseInfoTask constructor.
	 *
	 * @param project        the project to display notifications in
	 * @param identification exercise identification
	 */
	public GetExerciseInfoTask(@Nonnull final Project project, @Nonnull final Identification identification) {
		super(project, "Load Exercise Info", false);
		this.identification = identification;
	}
	
	@Override
	protected Exercise compute(@NotNull final ProgressIndicator indicator) throws RuntimeException {
		try {
			return Api.call(this.myProject, dodona -> this.identification.getCourseId()
				.map(course -> dodona.exercises().get.getAllByMe(course, identification.getExerciseId()))
				.orElseGet(() -> dodona.submissions().getAllByMe(identification.getExerciseId()))
			);
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
