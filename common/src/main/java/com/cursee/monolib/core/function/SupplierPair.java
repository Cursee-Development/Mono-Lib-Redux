package com.cursee.monolib.core.function;

import java.util.function.Supplier;

public class SupplierPair<A, B> {

    private final Supplier<A> aSupplier;
    private final Supplier<B> bSupplier;

    public SupplierPair(A a, B b) {
        this.aSupplier = () -> a;
        this.bSupplier = () -> b;
    }

    public final A getA() {
        return aSupplier.get();
    }

    public final Supplier<A> getASupplier() {
        return aSupplier;
    }

    public final B getB() {
        return bSupplier.get();
    }

    public final Supplier<B> getBSupplier() {
        return bSupplier;
    }
}
