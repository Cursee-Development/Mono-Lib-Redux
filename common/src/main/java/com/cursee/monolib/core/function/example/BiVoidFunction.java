package com.cursee.monolib.core.function.example;

/**
 * Example Usage:
 *
 * <pre>{@code
 * BiVoidFunction<String, Integer> stringOperation = (s, i) -> System.out.println(String.valueOf(i) + " " + s.toUpperCase());
 * stringOperation.apply("hello!); // output: HELLO!
 * }</pre>
 */
@FunctionalInterface
public interface BiVoidFunction<K, V> {
    void apply(K k, V v);
}
