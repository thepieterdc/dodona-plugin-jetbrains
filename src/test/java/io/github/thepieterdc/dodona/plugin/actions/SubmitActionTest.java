/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.actions;

import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import io.github.thepieterdc.dodona.plugin.TestData;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests SubmitAction.
 */
public class SubmitActionTest extends BasePlatformTestCase {
	@Override
	protected String getTestDataPath() {
		return TestData.getTestDataPath();
	}
	
	/**
	 * Tests whether the action can be invoked when no file is opened.
	 */
	@Test
	public void testInvocationNoFile() {
		final SubmitAction action = new SubmitAction();
		final Presentation presentation = this.myFixture.testAction(action);
		Assert.assertFalse(presentation.isEnabledAndVisible());
	}
}