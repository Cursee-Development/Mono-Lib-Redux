package com.cursee.monolib.core.util.config.v2;

import java.util.LinkedHashMap;

public class ConfigObject {

    private final LinkedHashMap<String, ConfigValue<?>> values = new LinkedHashMap<>();

    public <T> ConfigValue<T> define(String key, T defaultValue) {
        ConfigValue<T> value = new ConfigValue<>(key, defaultValue, defaultValue);
        values.put(key, value);
        return value;
    }

    public LinkedHashMap<String, ConfigValue<?>> getValues() {
        return values;
    }

    public ConfigValue<?> get(String key) {
        if (values.get(key) == null) throw new IllegalStateException("No value present for " + key);
        return values.<ConfigValue<?>>get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> ConfigValue<T> getValueOfType(String key, Class<T> clazz) {

        ConfigValue<T> obj = (ConfigValue<T>) get(key);

        if (!clazz.isInstance(obj)) throw new IllegalStateException(" expected " + clazz.getSimpleName() + " but got " + obj.getClass().getSimpleName() + " for key: " + key);

        return obj;
    }
}
