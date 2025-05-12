package com.cursee.monolib.core.util.config;

import com.cursee.monolib.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ISimpleConfig {

    String identifier();
    LinkedHashMap<String, Object> values();

    default Set<String> keys() {
        return values().keySet();
    }
    
    default boolean hasKey(String s) {
        return values().containsKey(s);
    }

    default void putEntry(String key, Object value, boolean replaceExisting) {
        if (values().containsKey(key) && !replaceExisting) throw new IllegalStateException(identifier() + ": Attempted to overwrite " + key);
        values().put(key, value);
    }

    default Object get(String key) {
        if (!values().containsKey(key)) throw new IllegalStateException(identifier() + ": Attempted to get missing key " + key);
        if (values().get(key) == null) throw new IllegalStateException(identifier() + ": Attempted to get null value " + key);
        return values().get(key);
    }

    @SuppressWarnings("unchecked")
    private <T> T getObjectOfType(String key, Class<T> clazz) {

        Object obj = get(key);

        if (!clazz.isInstance(obj)) throw new IllegalStateException(identifier() + " expected " + clazz.getSimpleName() + " but got " + obj.getClass().getSimpleName() + " for key: " + key);

        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    default <T> T getOrDefault(String key, T defaultValue) {
        if (!values().containsKey(key)) {
            Constants.LOG.info("{} did not find key \"{}\", providing default: {}", identifier(), key, String.valueOf(defaultValue));
            return defaultValue;
        }
        return getObjectOfType(key, (Class<T>) defaultValue.getClass());
    }

    default <T> Optional<T> getOptional(String key, Class<T> clazz) {
        Object obj = values().get(key);
        if (!clazz.isInstance(obj)) return Optional.empty();
        return Optional.of(clazz.cast(obj));
    }

    default String getString(String key) {
        return getObjectOfType(key, String.class);
    }

    default Integer getInt(String key) {
        return getObjectOfType(key, Integer.class);
    }

    default Float getFloat(String key) {
        return getObjectOfType(key, Float.class);
    }

    default Boolean getBoolean(String key) {
        return getObjectOfType(key, Boolean.class);
    }

    private List<?> getArray(String key) {
        Object obj = get(key);
        if (!(obj instanceof List<?>)) return null;
        return (List<?>) obj;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getArrayOfType(String key, Class<T> clazz) {
        List<?> list = getArray(key);
        if (list == null) throw new IllegalStateException(identifier() + " failed to get key as " + clazz.getSimpleName() + " list: " + key);

        for (Object item : list) {
            if (!clazz.isInstance(item)) {
                throw new IllegalStateException(identifier() + " expected " + clazz.getSimpleName() + " but got " + item.getClass().getSimpleName() + " for item in list: " + key);
            }
        }
        return (List<T>) list;
    }

    default List<String> getArrayOfString(String key) {
        return getArrayOfType(key, String.class);
    }

    default List<Integer> getArrayOfInt(String key) {
        return getArrayOfType(key, Integer.class);
    }

    default List<Float> getArrayOfFloat(String key) {
        return getArrayOfType(key, Float.class);
    }

    default List<Boolean> getArrayOfBoolean(String key) {
        return getArrayOfType(key, Boolean.class);
    }
}
