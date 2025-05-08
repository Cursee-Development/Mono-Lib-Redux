package com.cursee.monolib.core.util.config;

import com.cursee.monolib.Constants;
import oshi.util.tuples.Pair;

import java.nio.file.*;
import java.util.*;

public class SimpleConfig {

    private final String identifier;
    private final LinkedHashMap<String, Object> values;

    public SimpleConfig(String identifier) {
        this(identifier, new LinkedHashMap<String, Object>());
    }

    public SimpleConfig(String identifier, LinkedHashMap<String, Object> defaults) {
        this.identifier = identifier;
        this.values = defaults;
    }

    public SimpleConfig(String identifier, LinkedList<Pair<String, Object>> defaults) {
        this(identifier, mapFromPairs(identifier, defaults));
    }

    public static SimpleConfig fromConfigEntryLinkedList(String identifier, LinkedList<SimpleConfigEntry> entryList) {
        SimpleConfig obj = new SimpleConfig(identifier);

        entryList.forEach(entry -> {
            obj.set(entry.key(), entry.value(), false);
        });

        return obj;
    }

    private static LinkedHashMap<String, Object> mapFromPairs(String identifier, List<Pair<String, Object>> defaults) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        for (Pair<String, Object> pair : defaults) {
            if (result.containsKey(pair.getA())) {
                throw new IllegalStateException("The following key has duplicate values for " + identifier + ": " + pair.getA());
            }
            result.put(pair.getA(), pair.getB());
        }
        return result;
    }

    public Set<String> keys() {
        return values.keySet();
    }

    public void set(String key, Object value, boolean replaceExisting) {
        if (!replaceExisting && values.containsKey(key)) throw new IllegalStateException(identifier + " attempted to write duplicate key: " + key);
        values.put(key, value);
    }

    protected Object get(String key) {
        if (!values.containsKey(key) || values.get(key) == null) throw new IllegalStateException("Key not present for " + identifier + ": " + key);
        return values.get(key);
    }

    @SuppressWarnings("unchecked")
    private <T> T getObjectOfType(String key, Class<T> clazz) {

        Object obj = get(key);

        if (!clazz.isInstance(obj)) throw new IllegalStateException(identifier + " expected " + clazz.getSimpleName() + " but got " + obj.getClass().getSimpleName() + " for key: " + key);

        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getOrDefault(String key, T defaultValue) {
        if (!values.containsKey(key)) {
            Constants.LOG.info("{} did not find key \"{}\", providing default: {}", identifier, key, String.valueOf(defaultValue));
            return defaultValue;
        }
        return getObjectOfType(key, (Class<T>) defaultValue.getClass());
    }

    protected <T> Optional<T> getOptional(String key, Class<T> clazz) {
        Object obj = values.get(key);
        if (!clazz.isInstance(obj)) return Optional.empty();
        return Optional.of(clazz.cast(obj));
    }

    public String getString(String key) {
        return getObjectOfType(key, String.class);
    }

    public Integer getInt(String key) {
        return getObjectOfType(key, Integer.class);
    }

    public Float getFloat(String key) {
        return getObjectOfType(key, Float.class);
    }

    public Boolean getBoolean(String key) {
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
        if (list == null) throw new IllegalStateException(identifier + " failed to get key as " + clazz.getSimpleName() + " list: " + key);

        for (Object item : list) {
            if (!clazz.isInstance(item)) {
                throw new IllegalStateException(identifier + " expected " + clazz.getSimpleName() + " but got " + item.getClass().getSimpleName() + " for item in list: " + key);
            }
        }
        return (List<T>) list;
    }

    public List<String> getArrayOfString(String key) {
        return getArrayOfType(key, String.class);
    }

    public List<Integer> getArrayOfInt(String key) {
        return getArrayOfType(key, Integer.class);
    }

    public List<Float> getArrayOfFloat(String key) {
        return getArrayOfType(key, Float.class);
    }

    public List<Boolean> getArrayOfBoolean(String key) {
        return getArrayOfType(key, Boolean.class);
    }

    public boolean hasKey(String s) {
        return values.containsKey(s);
    }

    public void save(Path path) {
        SimpleConfigIO.save(this, path);
    }

    public SimpleConfig load(Path path) {
        return SimpleConfigIO.load(this, path);
    }

    public SimpleConfig load(List<String> defaults) {
        return SimpleConfigIO.load(this, defaults);
    }
}
