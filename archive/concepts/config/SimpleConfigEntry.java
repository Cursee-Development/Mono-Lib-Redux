package com.cursee.monolib.core.util.config;

public record SimpleConfigEntry(String key, Object value) {

    public SimpleConfigEntry(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
