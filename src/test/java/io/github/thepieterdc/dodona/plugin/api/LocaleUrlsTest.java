/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests LocaleUrls.
 */
class LocaleUrlsTest {
    @Test
    void applyKeepsUrlWithoutLocale() {
        assertEquals(
                "https://dodona.be/nl/submissions/1/", LocaleUrls.apply("https://dodona.be/nl/submissions/1/", null));
    }

    @Test
    void applyReplacesLocale() {
        assertEquals(
                "https://dodona.be/en/submissions/1/", LocaleUrls.apply("https://dodona.be/nl/submissions/1/", "en"));
    }

    @Test
    void extractFindsLocale() {
        assertEquals(
                "en", LocaleUrls.extract("https://dodona.be/en/activities/5/").orElse(null));
    }

    @Test
    void extractIgnoresUrlsWithoutLocale() {
        assertFalse(LocaleUrls.extract("https://dodona.be/activities/5/").isPresent());
    }
}
