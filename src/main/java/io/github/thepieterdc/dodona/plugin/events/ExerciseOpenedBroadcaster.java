/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.events;

import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import io.github.thepieterdc.dodona.plugin.exercise.CurrentExerciseListener;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.exercise.identification.IdentificationService;
import io.github.thepieterdc.dodona.plugin.settings.DodonaProjectSettings;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Listens for file manager events to determine the current opened exercise. This
 * should be listed directly in plugin.xml when the API has matured.
 */
public class ExerciseOpenedBroadcaster implements FileEditorManagerListener {
	private final IdentificationService identificationService;
	
	private final MessageBus bus;
	private final Project project;
	
	/**
	 * ExerciseOpenedBroadcaster constructor.
	 *
	 * @param project current active project
	 */
	public ExerciseOpenedBroadcaster(final Project project) {
		this.bus = project.getMessageBus();
		this.identificationService = IdentificationService.getInstance();
		this.project = project;
		
		// Listen for the current opened exercise.
		this.bus.connect().subscribe(
			FileEditorManagerListener.FILE_EDITOR_MANAGER,
			this
		);
	}
	
	@Override
	public void selectionChanged(@NotNull final FileEditorManagerEvent event) {
		// Find the current opened file.
		final VirtualFile vFile = event.getNewFile();
		
		// Identify the current opened exercise.
		final Optional<Identification> identification = Optional.ofNullable(vFile)
			.flatMap(file -> this.identificationService.identify(this.project, file));
		
		// Set the project course in the settings.
		identification.flatMap(Identification::getCourseId).ifPresent(course ->
			DodonaProjectSettings.getInstance(this.project).setCourseId(course)
		);
		
		// Set the series in the settings.
		identification.flatMap(Identification::getSeriesId).ifPresent(series ->
			DodonaProjectSettings.getInstance(this.project).setSeriesId(series)
		);
		
		// Publish the event.
		this.bus.syncPublisher(CurrentExerciseListener.CHANGED_TOPIC)
			.onCurrentExercise(vFile, identification.orElse(null));
	}
}
