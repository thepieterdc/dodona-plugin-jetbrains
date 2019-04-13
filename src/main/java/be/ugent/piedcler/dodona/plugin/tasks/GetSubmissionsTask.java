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
import be.ugent.piedcler.dodona.resources.submissions.PartialSubmission;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

/**
 * Fetches the submissions of the current logged in user for a given exercise.
 */
public class GetSubmissionsTask extends Task.WithResult<List<PartialSubmission>, RuntimeException> {
	private final Identification identification;
	
	/**
	 * GetSubmissionsTask constructor.
	 *
	 * @param project        the project to display notifications in
	 * @param identification exercise details
	 */
	public GetSubmissionsTask(@Nonnull final Project project, @Nonnull final Identification identification) {
		super(project, "Load Submissions", false);
		this.identification = identification;
	}
	
	@Override
	protected List<PartialSubmission> compute(@NotNull final ProgressIndicator indicator) throws RuntimeException {
		try {
			return Api.call(this.myProject, dodona -> this.identification.getCourseId()
				.map(course -> dodona.submissions().getAllByMe(course, this.identification.getExerciseId()))
				.orElseGet(() -> dodona.submissions().getAllByMe(this.identification.getExerciseId()))
			);
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
