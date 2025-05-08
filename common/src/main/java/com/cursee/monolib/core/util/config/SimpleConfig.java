package com.cursee.monolib.core.util.config;

import oshi.util.tuples.Pair;

import java.nio.file.*;
import java.util.*;

public class SimpleConfig implements ISimpleConfig {

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

    @Override
    public String identifier() {
        return this.identifier;
    }

    @Override
    public LinkedHashMap<String, Object> values() {
        return this.values;
    }

    private static LinkedHashMap<String, Object> mapFromPairs(String identifier, List<Pair<String, Object>> defaults) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        for (Pair<String, Object> pair : defaults) {
            if (result.containsKey(pair.getA())) {
                throw new IllegalStateException(identifier + ": Attempted to write duplicate entry for " + pair.getA());
            }
            result.put(pair.getA(), pair.getB());
        }
        return result;
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
