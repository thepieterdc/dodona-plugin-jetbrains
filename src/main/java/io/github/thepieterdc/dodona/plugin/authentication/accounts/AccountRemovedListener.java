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
 * Listens for removal of accounts.
 */
@FunctionalInterface
public interface AccountRemovedListener {
	Topic<AccountRemovedListener> REMOVED_TOPIC = Topic.create(
		"The default account has been removed",
		AccountRemovedListener.class
	);
	
	/**
	 * Called when the default account has been removed.
	 */
	void removed();
}
