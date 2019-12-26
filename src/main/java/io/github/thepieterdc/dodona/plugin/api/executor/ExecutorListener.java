/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api.executor;

import com.intellij.util.messages.Topic;

/**
 * Listener for new executors.
 */
@FunctionalInterface
public interface ExecutorListener {
	Topic<ExecutorListener> UPDATED_TOPIC = Topic.create(
		"The executor has been updated",
		ExecutorListener.class
	);
	
	/**
	 * Called when the executor has been replaced in the holder.
	 */
	void updated();
}
