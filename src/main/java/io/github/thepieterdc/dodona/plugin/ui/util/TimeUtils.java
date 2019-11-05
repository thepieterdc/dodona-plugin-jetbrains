/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.util;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import io.reactivex.annotations.NonNull;

import java.time.LocalDate;
import java.time.chrono.ChronoZonedDateTime;

/**
 * Utilities for handling time.
 */
public final class TimeUtils {
	private static final long MILLIS_PER_SECOND = 1000L;
	
	/**
	 * Gets a time ago or time left sentence for the given timestamp, relative
	 * to now.
	 *
	 * @param timestamp the timestamp to compare
	 * @return the sentence
	 */
	@NonNull
	public static String fuzzy(final ChronoZonedDateTime<LocalDate> timestamp) {
		return TimeAgo.using(timestamp.toEpochSecond() * MILLIS_PER_SECOND);
	}
}
