/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Project-specific plugin settings.
 */
@State(name = DodonaProjectSettings.STATE_NAME, storages = @Storage(DodonaProjectSettings.STORAGE_FILE))
public class DodonaProjectSettings implements PersistentStateComponent<DodonaProjectSettings.State> {
	@NonNls
	static final String STATE_NAME = "DodonaProjectSettings";
	
	@NonNls
	static final String STORAGE_FILE = "dodona.xml";
	
	private State state = new State();
	
	/**
	 * Gets the course id.
	 *
	 * @return the course id
	 */
	public long getCourseId() {
		return this.state.course_id;
	}
	
	/**
	 * Gets an instance of the project settings.
	 *
	 * @param project the active project
	 * @return the instance
	 */
	@Nonnull
	public static DodonaProjectSettings getInstance(@Nonnull final Project project) {
		return ServiceManager.getService(project, DodonaProjectSettings.class);
	}
	
	@Nonnull
	@Override
	public State getState() {
		return this.state;
	}
	
	@Override
	public void loadState(@NotNull final State newState) {
		this.state = newState;
	}
	
	/**
	 * Sets the course id.
	 *
	 * @param id the course id
	 */
	public void setCourseId(final long id) {
		this.state.course_id = id;
	}
	
	/**
	 * Setting values.
	 */
	public static class State {
		public long course_id = 0L;
	}
}