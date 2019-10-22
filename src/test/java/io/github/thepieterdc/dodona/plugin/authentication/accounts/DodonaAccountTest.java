/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.authentication.accounts;

import io.github.thepieterdc.random.textual.RandomStringGenerator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests DodonaAccount.
 */
public class DodonaAccountTest {
	private static final RandomStringGenerator strings = new RandomStringGenerator(20);
	
	/**
	 * Tests DodonaAccount#getEmail().
	 */
	@Test
	public void testGetEmail() {
		final String email = strings.generate();
		final DodonaAccount account = new DodonaAccount(email, "", "");
		Assert.assertNotNull(account);
		Assert.assertEquals(email, account.getEmail());
	}
	
	/**
	 * Tests DodonaAccount#getFullName().
	 */
	@Test
	public void testGetFullName() {
		final String fullName = strings.generate();
		final DodonaAccount account = new DodonaAccount("", fullName, "");
		Assert.assertNotNull(account);
		Assert.assertEquals(fullName, account.getFullName());
	}
	
	/**
	 * Tests DodonaAccount#getServer().
	 */
	@Test
	public void testGetServer() {
		final String server = strings.generate();
		final DodonaAccount account = new DodonaAccount("", "", server);
		Assert.assertNotNull(account);
		Assert.assertEquals(server, account.getServer());
	}
	
	/**
	 * Tests whether the id is unique.
	 */
	@Test
	public void testUniqueId() {
		final DodonaAccount account1 = new DodonaAccount(
			strings.generate(),
			strings.generate(),
			strings.generate()
		);
		Assert.assertNotNull(account1);
		
		final DodonaAccount account2 = new DodonaAccount(
			strings.generate(),
			strings.generate(),
			strings.generate()
		);
		Assert.assertNotNull(account2);
		
		Assert.assertNotEquals(account1.getId(), account2.getId());
		Assert.assertNotEquals(account1, account2);
	}
}