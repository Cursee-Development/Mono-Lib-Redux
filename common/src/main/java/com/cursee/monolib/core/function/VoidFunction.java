package com.cursee.monolib.core.function;

/**
 * Example Usage:
 *
 * <pre>{@code
 * VoidFunction<String> stringOperation = s -> System.out.println(s.toUpperCase());
 * stringOperation.apply("hello!); // output: HELLO!
 * }</pre>
 */
@FunctionalInterface
public interface VoidFunction<T> {
    void apply(T t);
}
