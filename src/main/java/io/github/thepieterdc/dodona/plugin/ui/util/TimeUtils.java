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
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.reactivex.annotations.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.chrono.ChronoZonedDateTime;

/**
 * Utilities for handling time.
 */
public enum TimeUtils {
	;
	public static final long HOURS_PER_DAY = 24L;
	
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
	
	/**
	 * Gets a human readable time sentence for the given duration.
	 *
	 * @param duration the duration to format
	 * @return the sentence
	 */
	@NonNull
	public static String fuzzy(final Duration duration) {
		if (duration.toMillis() < 10L * MILLIS_PER_SECOND) {
			return DodonaBundle.message("duration.now");
		}
		
		if (duration.toMinutes() < 2L) {
			return DodonaBundle.message("duration.seconds", duration.toMillis() / MILLIS_PER_SECOND);
		}
		
		if (duration.toHours() < 2L) {
			return DodonaBundle.message("duration.minutes", duration.toMinutes());
		}
		
		return duration.toDays() < 2L
			? DodonaBundle.message("duration.hours", duration.toHours())
			: DodonaBundle.message("duration.days", duration.toDays());
	}
}
