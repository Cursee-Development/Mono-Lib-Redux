package com.cursee.monolib.core.util.config.v2;

import oshi.util.tuples.Pair;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Config implements IMapWrapper {

    private final String identifier;
    private final LinkedHashMap<String, Object> root;

    public Config(String identifier) {
        this.identifier = identifier;
        this.root = new LinkedHashMap<String, Object>();
    }

    public Config(String identifier, LinkedHashMap<String, Object> defaults) {
        this.identifier = identifier;
        this.root = new LinkedHashMap<String, Object>(defaults);
    }

    public Config(String identifier, LinkedList<Pair<String, Object>> defaults) {
        this(identifier);
        defaults.forEach(pair -> {
            this.root.put(pair.getA(), pair.getB());
        });
    }

    public static Config createCommon(String identifier) {
        return new Config(identifier + "-common");
    }

    public static Config createCommonFromDefaults(String identifier, LinkedHashMap<String, Object> defaults) {
        return new Config(identifier + "-common", defaults);
    }

    public static Config createCommonFromDefaults(String identifier, LinkedList<Pair<String, Object>> defaults) {
        return new Config(identifier + "-common", defaults);
    }

    @Override
    public String identifier() {
        return this.identifier;
    }

    @Override
    public LinkedHashMap<String, Object> root() {
        return this.root;
    }
}
