/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.Topic;

import javax.annotation.Nullable;

/**
 * An exercise file has been opened.
 */
@FunctionalInterface
public interface CurrentExerciseListener {
	Topic<CurrentExerciseListener> CHANGED_TOPIC = Topic.create(
		"Current exercise changed",
		CurrentExerciseListener.class
	);
	
	/**
	 * Called when a new exercise is opened. The file (and identification) will
	 * be null if there are no opened files. The identification will be null if
	 * the exercise could not be identified.
	 *
	 * @param identification the identification of the exercise
	 */
	void onCurrentExercise(@Nullable VirtualFile file,
	                       @Nullable Identification identification);
}
