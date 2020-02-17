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
import com.intellij.util.messages.MessageBus;
import io.github.thepieterdc.dodona.plugin.settings.listeners.ProjectCourseListener;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

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
	
	private final MessageBus bus;
	
	/**
	 * DodonaProjectSettings constructor.
	 *
	 * @param project the current project
	 */
	public DodonaProjectSettings(final Project project) {
		this.bus = project.getMessageBus();
	}
	
	/**
	 * Gets the course id if set.
	 *
	 * @return the course id
	 */
	@Nonnull
	public Optional<Long> getCourseId() {
		return Optional.of(this.state.course_id).filter(id -> id > 0L);
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
	
	/**
	 * Gets the series id if set.
	 *
	 * @return the series id
	 */
	@Nonnull
	public Optional<Long> getSeriesId() {
		return Optional.of(this.state.series_id).filter(id -> id > 0L);
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
		this.bus.syncPublisher(ProjectCourseListener.CHANGED_TOPIC).onCourseChanged(id);
	}
	
	/**
	 * Sets the series id.
	 *
	 * @param id the series id
	 */
	public void setSeriesId(final long id) {
		this.state.series_id = id;
		this.bus.syncPublisher(ProjectCourseListener.CHANGED_TOPIC).onCourseChanged(id);
	}
	
	/**
	 * Setting values.
	 */
	public static class State {
		public long course_id = 0L;
		public long series_id = 0L;
	}
}