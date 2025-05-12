package com.cursee.monolib.core.function;

/**
 * Example Usage:
 *
 * <pre>{@code
 * List<String> items = List.of("apple", "banana", "cherry");
 *
 * IndexedConsumer<String> printer = (index, value) ->
 *     System.out.println(index + ": " + value);
 *
 * for (int i = 0; i < items.size(); i++) {
 *     printer.accept(i, items.get(i));
 * }
 * }</pre>
 */
@FunctionalInterface
public interface IndexedConsumer<T> {
    void accept(int index, T value);
}
