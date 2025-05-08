package com.cursee.monolib.core.util.config.v2;

import java.util.LinkedHashMap;
import java.util.Set;

public interface IMapWrapper {

    String identifier();
    LinkedHashMap<String, Object> root();

    default Set<String> keys() {
        return root().keySet();
    }

    default boolean has(String key) {
        return root().containsKey(key);
    }

    default void set(String key, Object obj) {
        if (has(key)) throw new IllegalStateException(identifier() + ": Attempted to overwrite key: " + key);
        root().put(key, obj);
    }

    default Object get(String key) {
        if (!has(key)) throw new IllegalStateException(identifier() + ": Attempted to get invalid key: " + key);
        else if (root().get(key) == null) throw new IllegalStateException(identifier() + ": Attempted to get empty value for key: " + key);
        return root().get(key);
    }

    /** Get the current mapped object, or set it to the provided object and return that. */
    default Object getOrSet(String key, Object obj) {
        if (has(key)) return get(key);
        else set(key, obj);
        return obj;
    }

    /** Removes the mapping, but not the full entry. */
    default void delete(String key) {
        root().remove(key);
    }

    /** Removes the full entry. */
    default void delete(String key, Object obj) {
        root().remove(key ,obj);
    }

    default void clear() {
        root().clear();
    }
}
