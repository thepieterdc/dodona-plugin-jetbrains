/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.accounts;

import com.intellij.util.xmlb.annotations.Attribute;
import com.intellij.util.xmlb.annotations.Tag;
import io.github.thepieterdc.dodona.DodonaClient;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

/**
 * An account that can be used on the Dodona platform.
 */
@Tag("account")
public class DodonaAccount {
	@Attribute("email")
	@Nonnull
	private final String email;
	
	@Attribute("full_name")
	@Nonnull
	private final String fullName;
	
	@Attribute("id")
	@Nonnull
	private final String id;
	
	@Attribute("server")
	@Nonnull
	private final String server;
	
	/**
	 * DodonaAccount constructor.
	 *
	 * @param email    email of the account
	 * @param fullName full name of the user
	 * @param server   Dodona server
	 */
	DodonaAccount(final String email, final String fullName, final String server) {
		this.email = email;
		this.fullName = fullName;
		this.id = UUID.randomUUID().toString();
		this.server = server;
	}
	
	/**
	 * DodonaAccount constructor (serialization).
	 */
	private DodonaAccount() {
		this.email = "";
		this.fullName = "";
		this.id = "";
		this.server = DodonaClient.DEFAULT_HOST;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) return true;
		return obj instanceof DodonaAccount && Objects.equals(this.id, ((DodonaAccount) obj).id);
	}
	
	/**
	 * Gets the email address of the account.
	 *
	 * @return the email address
	 */
	@NonNls
	@Nonnull
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Gets the full name of the account.
	 *
	 * @return the full name
	 */
	@Nonnull
	public String getFullName() {
		return this.fullName;
	}
	
	/**
	 * Gets the id of the account.
	 *
	 * @return the account id
	 */
	@Nonnull
	public String getId() {
		return this.id;
	}
	
	/**
	 * Gets the Dodona server.
	 *
	 * @return the server
	 */
	@NonNls
	@Nonnull
	public String getServer() {
		return this.server;
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	@NonNls
	@Override
	public String toString() {
		return this.email + " @ " + this.server;
	}
}
