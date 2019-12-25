/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.accounts;

import com.intellij.util.messages.Topic;

/**
 * Listens for changes to the account.
 */
@FunctionalInterface
public interface AccountListener {
	Topic<AccountListener> UPDATED_TOPIC = Topic.create(
		"The default account has been changed",
		AccountListener.class
	);
	
	/**
	 * Called when the default account has been changed.
	 */
	void updated();
}
