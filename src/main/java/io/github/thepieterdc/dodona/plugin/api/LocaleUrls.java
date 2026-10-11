/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utilities to deal with the locale segment of Dodona urls.
 */
public enum LocaleUrls {
    ;

    private static final Pattern LOCALE = Pattern.compile("^(https?://[^/]+/)(nl|en)(/|$)");

    /**
     * Replaces the locale in the given Dodona url.
     *
     * @param url    the url
     * @param locale the locale to use, or null to keep the url unchanged
     * @return the url in the given locale
     */
    @Nonnull
    public static String apply(final String url, @Nullable final String locale) {
        if (locale == null) {
            return url;
        }
        return LOCALE.matcher(url).replaceFirst(m -> Matcher.quoteReplacement(m.group(1) + locale + m.group(3)));
    }

    /**
     * Extracts the locale from the given Dodona url.
     *
     * @param url the url
     * @return the locale if present
     */
    @Nonnull
    public static Optional<String> extract(final String url) {
        final Matcher matcher = LOCALE.matcher(url.trim());
        return matcher.find() ? Optional.of(matcher.group(2)) : Optional.empty();
    }
}
