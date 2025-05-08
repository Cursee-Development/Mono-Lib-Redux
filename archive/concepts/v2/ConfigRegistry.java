package com.cursee.monolib.core.util.config.v2;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigRegistry {
    private static final ConfigRegistry INSTANCE = new ConfigRegistry();
    private final Map<String, ConfigCategory> categories = new HashMap<>();

    private ConfigRegistry() {}

    public static ConfigRegistry get() {
        return INSTANCE;
    }

    public ConfigCategory getOrCreateCategory(String name) {
        return categories.computeIfAbsent(name, ConfigCategory::new);
    }

    public ConfigCategory getCategory(String name) {
        return categories.get(name);
    }

    public Map<String, ConfigCategory> getAllCategories() {
        return Collections.unmodifiableMap(categories);
    }

    public void saveAll(File baseDir) throws Exception {
        for (Map.Entry<String, ConfigCategory> category : categories.entrySet()) {
            for (Map.Entry<String, ConfigObject> entry : category.getValue().getAll().entrySet()) {
                String filename = entry.getKey().replace(':', '_') + ".json";
                ConfigManager manager = new ConfigManager(new File(baseDir, filename), entry.getValue());
                manager.save();
            }
        }
    }

    public void loadAll(File baseDir) throws Exception {
        for (Map.Entry<String, ConfigCategory> category : categories.entrySet()) {
            for (Map.Entry<String, ConfigObject> entry : category.getValue().getAll().entrySet()) {
                String filename = entry.getKey().replace(':', '_') + ".json";
                ConfigManager manager = new ConfigManager(new File(baseDir, filename), entry.getValue());
                manager.load();
            }
        }
    }
}
