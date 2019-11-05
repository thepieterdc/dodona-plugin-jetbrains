/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;

import static io.github.thepieterdc.random.numerical.RandomLongGenerator.between;

/**
 * Tests DeadlineListCellRenderer.
 */
@SuppressWarnings("UnqualifiedStaticUsage")
public class DeadlineListCellRendererTest {
	/**
	 * Tests .humanize() using a missed deadline.
	 */
	@Test
	public void testHumanizeMissed() {
		final ZonedDateTime missed = ZonedDateTime.now().minusMinutes(1L);
		Assert.assertEquals(DodonaBundle.message("deadline.missed"),
			DeadlineListCellRenderer.humanize(missed)
		);
	}
	
	/**
	 * Tests .humanize() using a deadline within 1 minute.
	 */
	@Test
	public void testHumanizeSubMinute() {
		final ZonedDateTime deadline = ZonedDateTime.now().plusSeconds(between(1L, 59L).generate());
		Assert.assertEquals(
			DodonaBundle.message("deadline.now"),
			DeadlineListCellRenderer.humanize(deadline)
		);
	}
}