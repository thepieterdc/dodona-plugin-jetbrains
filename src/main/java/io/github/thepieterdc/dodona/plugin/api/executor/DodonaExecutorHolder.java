/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api.executor;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.messages.MessageBus;

import javax.annotation.Nonnull;

/**
 * Holds a reference to an executor to allow updating.
 */
public final class DodonaExecutorHolder {
	private final MessageBus bus;
	private DodonaExecutor executor;
	
	/**
	 * DodonaRequestFactory constructor.
	 *
	 * @param executor the executor
	 */
	public DodonaExecutorHolder(final DodonaExecutor executor) {
		this.bus = ApplicationManager.getApplication().getMessageBus();
		this.executor = executor;
	}
	
	/**
	 * Gets the executor. This value may not be passed nor saved to a local
	 * variable.
	 *
	 * @return the executor
	 */
	@Nonnull
	public DodonaExecutor getExecutor() {
		return this.executor;
	}
	
	/**
	 * Replaces this executor by a missing executor, causing all requests to
	 * fail.
	 */
	public void invalidate() {
		this.executor = MissingExecutorImpl.INSTANCE;
	}
	
	/**
	 * Replaces the executor.
	 *
	 * @param nw the new executor
	 */
	public void update(final DodonaExecutor nw) {
		this.executor = nw;
		this.bus.syncPublisher(ExecutorListener.UPDATED_TOPIC).updated();
	}
}
