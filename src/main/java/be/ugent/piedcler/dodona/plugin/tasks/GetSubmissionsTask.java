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
import be.ugent.piedcler.dodona.resources.submissions.PartialSubmission;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;

/**
 * Fetches the submissions of the current logged in user.
 */
public class GetSubmissionsTask extends Task.WithResult<Collection<PartialSubmission>, RuntimeException> {
	/**
	 * GetSubmissionsTask constructor.
	 *
	 * @param project the project to display notifications in
	 */
	public GetSubmissionsTask(@Nonnull final Project project) {
		super(project, "Load Submissions", false);
	}
	
	@Override
	protected Collection<PartialSubmission> compute(@NotNull final ProgressIndicator indicator) throws RuntimeException {
		try {
			return Api.call(this.myProject, dodona -> dodona.submissions().getAll(dodona.me()));
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
