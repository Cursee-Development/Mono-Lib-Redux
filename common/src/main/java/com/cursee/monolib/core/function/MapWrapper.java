package com.cursee.monolib.core.function;

import java.util.Map;

@FunctionalInterface
public interface MapWrapper<K, V> {

    Map<K, V> root();

    default void forAllValues(VoidFunction<V> function) {
        root().forEach((k, v) -> function.apply(v));
    }

    default void forAllEntries(BiVoidFunction<K, V> function) {
        root().forEach(function::apply);
    }
}
