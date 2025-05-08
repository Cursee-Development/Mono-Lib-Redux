package com.cursee.monolib.core.util.config.v2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigCategory {
    private final String name;
    private final Map<String, ConfigObject> objects = new HashMap<>();

    public ConfigCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void register(String id, ConfigObject config) {
        if (objects.containsKey(id))
            throw new IllegalArgumentException("ConfigObject with id '" + id + "' is already registered in category '" + name + "'");
        objects.put(id, config);
    }

    public ConfigObject get(String id) {
        return objects.get(id);
    }

    public Map<String, ConfigObject> getAll() {
        return Collections.unmodifiableMap(objects);
    }
}
