package com.cursee.monolib.core.config;

import java.util.concurrent.atomic.AtomicReference;

@Deprecated(since = "2.1.0", forRemoval = true)
public class SimpleConfigEntry<V> {

    private final String key;
    private final AtomicReference<V> value;

    public SimpleConfigEntry(String key, V value) {
        this.key = key;
        this.value = new AtomicReference<V>(value);
    }

    public final String getKey() {
        return key;
    }

    public final V getValue() {
        return value.get();
    }

    public final V get() {
        return this.getValue();
    }

    public void setValue(V value) {
        this.value.set(value);
    }
}
