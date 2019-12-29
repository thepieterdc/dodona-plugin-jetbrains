/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin;

import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * Data used in tests.
 */
public enum TestData {
	;
	
	@NonNls
	public static final String HELLO_WORLD_CORRECT_UNIDENTIFIED = "hello-world/HelloWorld.py";
	
	/**
	 * Gets the path to the testData directory.
	 *
	 * @return the directory
	 */
	@Nonnull
	public static String getTestDataPath() {
		return new File("testData/").getAbsolutePath();
	}
}
