package com.cursee.monolib.core.util;

@FunctionalInterface
public interface QuadFunction<I, J, K, L, R> {
    R apply(I i, J j, K k, L l);
}