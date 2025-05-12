package com.cursee.monolib.core.util;

@FunctionalInterface
public interface QuintFunction<I, J, K, L, M, R> {
    R apply(I i, J j, K k, L l, M m);
}