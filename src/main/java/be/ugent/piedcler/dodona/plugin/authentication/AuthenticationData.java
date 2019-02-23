/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.authentication;

/**
 * Wrapper for authentication data.
 */
public class AuthenticationData {
	private final String host;
	private final String token;
	
	/**
	 * AuthenticationData constructor.
	 *
	 * @param host  the hostname
	 * @param token the token
	 */
	private AuthenticationData(final String host,
	                           final String token) {
		this.host = host;
		this.token = token;
	}
	
	/**
	 * Creates a new AuthenticationData instance.
	 *
	 * @param host  the hostname
	 * @param token the token
	 * @return instance of AuthenticationData
	 */
	public static AuthenticationData create(final String host, final String token) {
		return new AuthenticationData(host, token.trim());
	}
	
	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public String getHost() {
		return this.host;
	}
	
	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}
}
