/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels.async;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBusConnection;
import io.github.thepieterdc.dodona.plugin.api.executor.ExecutorListener;
import io.github.thepieterdc.dodona.plugin.ui.Updatable;
import io.github.thepieterdc.dodona.plugin.ui.cards.LoadingCard;
import io.github.thepieterdc.dodona.plugin.ui.cards.NoConnectionCard;
import io.github.thepieterdc.dodona.plugin.ui.panels.CardPanelBase;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;

/**
 * A panel of which the content is asynchronously loaded.
 *
 * @param <T> type class of the content
 */
abstract class AsyncPanelBase<T> extends CardPanelBase implements Disposable, Updatable {
    @NonNls
    private static final String CARD_LOADING = "ASYNC_LOADING";

    @Nullable
    private MessageBusConnection connection;

    /**
     * AsyncPanel constructor.
     *
     * @param project     current active project
     * @param loadingText the loading text to display underneath the spinner
     */
    protected AsyncPanelBase(@Nullable final Project project, @Nls final String loadingText) {
        super(project);
        this.initialize(loadingText);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        // Refresh the contents when the default executor is updated. The connection is only held
        // while the panel is displayable, so that it is never leaked when nobody disposes the panel.
        this.disconnect();
        this.connection = ApplicationManager.getApplication().getMessageBus().connect();
        this.connection.subscribe(ExecutorListener.UPDATED_TOPIC, () -> {
            if (this.isDisplayable()) {
                this.requestUpdate();
            }
        });
    }

    @Override
    public void removeNotify() {
        this.disconnect();
        super.removeNotify();
    }

    private void disconnect() {
        if (this.connection != null) {
            this.connection.disconnect();
            this.connection = null;
        }
    }

    @Nonnull
    @Override
    protected NoConnectionCard createNoConnectionCard() {
        return NoConnectionCard.create(this);
    }

    @Override
    public void dispose() {
        this.disconnect();
    }

    /**
     * Gets the data to display in the content pane.
     *
     * @return request to fetch the data
     */
    @Nonnull
    protected abstract CompletableFuture<T> getData();

    /**
     * Adds the cards.
     *
     * @param loadingText the loading text to display underneath the spinner
     */
    protected void initialize(@Nls final String loadingText) {
        // Create a loading card.
        final JScrollPane loading = LoadingCard.create(loadingText, this).wrap();

        // Add the loading card.
        this.add(loading, CARD_LOADING);
    }

    /**
     * Shows the card that contains the loading screen.
     */
    protected void showLoadingCard() {
        this.showCard(CARD_LOADING);
    }
}
