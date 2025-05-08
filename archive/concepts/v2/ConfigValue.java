package com.cursee.monolib.core.util.config.v2;

/**
 * The root of our config hierarchy.
 * <p />
 * ConfigRegistry holds all ConfigCategory instances.
 * ConfigCategory instances hold multiple ConfigObject instances.
 * ConfigObject instances hold ConfigValue instances.
 */
public class ConfigValue<T> {

    private final String key;

    private T value;
    private final T defaultValue;

    public ConfigValue(String key, T value, T defaultValue) {
        this.key = key;
        this.value = value;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public T get() {
        return value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void set(T value) {
        this.value = value;
    }


}
