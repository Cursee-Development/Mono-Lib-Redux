package com.cursee.monolib.core.function;

import java.util.concurrent.CompletableFuture;

/**
 * Example Usage:
 *
 * <pre>{@code
 * AsyncFunction<String, Integer> fetchUserId = username ->
 *     CompletableFuture.supplyAsync(() -> username.hashCode());
 *
 * fetchUserId.apply("Jason").thenAccept(id ->
 *     System.out.println("Fetched user ID: " + id)
 * );
 * }</pre>
 */
@FunctionalInterface
public interface AsyncFunction<T, R> {
    CompletableFuture<R> apply(T t);
}
