/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.api;

import org.jetbrains.annotations.NonNls;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests DodonaServer.
 */
public class DodonaServerTest {
	@NonNls
	private static final String URL = "https://some.dodona.instance/";
	
	/**
	 * Tests .toDisplayName() using known servers.
	 */
	@Test
	public void testToDisplayNameKnown() {
		for (final DodonaServer server : DodonaServer.values()) {
			assertEquals(
				server.getDisplayName(),
				DodonaServer.toDisplayName(server.getUrl())
			);
		}
	}
	
	/**
	 * Tests .toDisplayName() using an unknown server.
	 */
	@Test
	public void testToDisplayNameUnknown() {
		assertEquals(
			URL,
			DodonaServer.toDisplayName(URL)
		);
	}
	
	/**
	 * Tests .toIcon() using known servers.
	 */
	@Test
	public void testToIconKnown() {
		for (final DodonaServer server : DodonaServer.values()) {
			assertEquals(
				server.getIcon(),
				DodonaServer.toIcon(server.getUrl())
			);
		}
	}
	
	/**
	 * Tests .toIcon() using an unknown server.
	 */
	@Test
	public void testToIconUnknown() {
		assertEquals(
			DodonaServer.LOCAL3000.getIcon(),
			DodonaServer.toIcon(URL)
		);
	}
	
	/**
	 * Tests #toString().
	 */
	@Test
	public void testToString() {
		for (final DodonaServer server : DodonaServer.values()) {
			assertEquals(
				server.getDisplayName(),
				server.toString()
			);
		}
	}
}