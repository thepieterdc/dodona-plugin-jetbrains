/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication;

/**
 * Provides an authentication token to the caller.
 */
@FunctionalInterface
public interface AuthenticationConsumer {
	/**
	 * Authenticates the current class using the provided authentication token.
	 *
	 * @param token the authentication token
	 */
	void authenticate(final String token);
}
