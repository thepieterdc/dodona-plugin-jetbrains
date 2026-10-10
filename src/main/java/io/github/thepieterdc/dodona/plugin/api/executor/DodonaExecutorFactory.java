/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api.executor;

import io.github.thepieterdc.dodona.DodonaBuilder;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.plugin.BuildConfig;
import io.github.thepieterdc.dodona.plugin.api.DodonaServer;
import javax.annotation.Nonnull;
import org.jetbrains.annotations.NonNls;

/**
 * Factory for DodonaExecutors.
 */
public enum DodonaExecutorFactory {
    ;

    @NonNls
    private static final String UA_PATTERN = "Plugin/JetBrains-%s";

    /**
     * Creates a new DodonaExecutor for the given server and token.
     *
     * @param server the Dodona server address
     * @param token  the authentication token
     * @return the request executor
     */
    public static DodonaExecutor create(final DodonaServer server, final String token) {
        return create(server.getUrl(), token);
    }

    /**
     * Creates a new DodonaExecutor for the given server and token.
     *
     * @param server the Dodona server address
     * @param token  the authentication token
     * @return the request executor
     */
    public static DodonaExecutor create(final String server, final String token) {
        final Thread thread = Thread.currentThread();
        final ClassLoader previous = thread.getContextClassLoader();
        thread.setContextClassLoader(DodonaExecutorFactory.class.getClassLoader());
        try {
            final DodonaClient client = DodonaBuilder.builder()
                    .authenticate(token)
                    .setHost(server)
                    .setUserAgent(getUserAgent())
                    .build();
            return new DodonaExecutorImpl(client);
        } finally {
            thread.setContextClassLoader(previous);
        }
    }

    /**
     * Creates a new DodonaExecutor that errors on every call.
     *
     * @return the request executor
     */
    @Nonnull
    public static DodonaExecutor createMissing() {
        return MissingExecutorImpl.INSTANCE;
    }

    /**
     * Gets the user agent to send in API calls.
     *
     * @return the user agent
     */
    @NonNls
    @Nonnull
    private static String getUserAgent() {
        return String.format(UA_PATTERN, BuildConfig.VERSION);
    }
}
