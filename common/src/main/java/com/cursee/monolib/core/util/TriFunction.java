package com.cursee.monolib.core.util;

@FunctionalInterface
public interface TriFunction<I, J, K, R> {
    R apply(I i, J j, K k);
}