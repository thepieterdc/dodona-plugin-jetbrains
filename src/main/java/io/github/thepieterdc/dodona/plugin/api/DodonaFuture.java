/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api;

import com.intellij.openapi.application.ActionsKt;
import com.intellij.openapi.progress.ProcessCanceledException;
import kotlin.Unit;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Augmented future.
 */
public class DodonaFuture<T> extends CompletableFuture<T> {
	private static final Executor EDT_EXECUTOR = command ->
		ActionsKt.runInEdt(null, () -> {
				command.run();
				return Unit.INSTANCE;
			}
		);
	
	/**
	 * Gets whether the given error is caused by cancellation of the user.
	 *
	 * @param error the error to check
	 * @return true if the error was caused by cancellation
	 */
	public static boolean cancelled(final Throwable error) {
		return error instanceof ProcessCanceledException
			|| error instanceof CancellationException
			|| error instanceof InterruptedException
			|| Optional.ofNullable(error.getCause()).map(DodonaFuture::cancelled).orElse(false);
	}
	
	/**
	 * Executes the given handler on the event thread.
	 *
	 * @param future  the future
	 * @param handler the handler to execute
	 */
	public static <T> void handleOnEdt(final CompletionStage<T> future,
	                                   final BiConsumer<? super T, ? super Throwable> handler) {
		future.handleAsync((BiFunction<T, Throwable, Object>) (t, throwable) -> {
			handler.accept(t, throwable);
			return Unit.INSTANCE;
		}, EDT_EXECUTOR);
	}
	
	/**
	 * Runs the given action on the event thread.
	 *
	 * @param handler the handler
	 * @return this value
	 */
	public CompletableFuture<T> whenCompleteRunOnEdt(final BiConsumer<? super T, ? super Throwable> handler) {
		return this.whenComplete((t, throwable) -> ActionsKt.runInEdt(null, () -> {
			handler.accept(t, throwable);
			return Unit.INSTANCE;
		}));
	}
}
