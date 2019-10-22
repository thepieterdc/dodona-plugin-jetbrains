/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.testutils;

import io.github.thepieterdc.dodona.resources.Resource;

import javax.annotation.Nonnull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Generates mocks for the given classes.
 */
public final class Mocking {
	@Nonnull
	public static <T extends Resource> T resource(final Class<T> resourceClass,
                                                  final long id) {
		final T mock = mock(resourceClass);
		when(mock.getId()).thenReturn(id);
		return mock;
	}
}
