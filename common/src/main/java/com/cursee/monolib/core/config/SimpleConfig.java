package com.cursee.monolib.core.config;

import com.cursee.monolib.platform.Services;
import com.cursee.monolib.util.toml.Keys;
import com.cursee.monolib.util.toml.Results;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Wrapper class for Toml and TomlWriter.
 * @see com.cursee.monolib.util.toml.Toml
 * @see com.cursee.monolib.util.toml.TomlWriter
 */
@Deprecated(since = "2.1.0", forRemoval = true)
public class SimpleConfig {

    public Map<String, Object> values = new HashMap<String, Object>();
    private final SimpleConfig defaults;

    public SimpleConfig() {
        this(null);
    }

    public SimpleConfig(SimpleConfig defaults) {
        this(defaults, new HashMap<String, Object>());
    }

    private SimpleConfig(SimpleConfig defaults, Map<String, Object> values) {
        this.values = values;
        this.defaults = defaults;
    }

    public static SimpleConfig getOrCreateCommon(String modId, SimpleConfigEntry<?>... references) {
        Set<SimpleConfigEntry<?>> referenceSet = new HashSet<>(Set.of());
        referenceSet.addAll(Arrays.asList(references));
        return getOrCreateCommon(modId, referenceSet);
    }

    public static SimpleConfig getOrCreateClient(String modId, SimpleConfigEntry<?>... references) {
        Set<SimpleConfigEntry<?>> referenceSet = new HashSet<>(Set.of());
        referenceSet.addAll(Arrays.asList(references));
        return getOrCreateClient(modId, referenceSet);
    }

    public static SimpleConfig getOrCreateDedicatedServer(String modId, SimpleConfigEntry<?>... references) {
        Set<SimpleConfigEntry<?>> referenceSet = new HashSet<>(Set.of());
        referenceSet.addAll(Arrays.asList(references));
        return getOrCreateDedicatedServer(modId, referenceSet);
    }

    public static SimpleConfig getOrCreateCommon(String modId, Set<SimpleConfigEntry<?>> references) {

        File CONFIG_DIR = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
        CONFIG_DIR.mkdirs();
        File CONFIG_FILE = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config" + File.separator + modId + "-common.toml");
        try {
            if (CONFIG_FILE.exists()) return read(CONFIG_FILE);
            else {
                Map<String, Object> defaults = new HashMap<>();
                references.forEach(simpleConfigEntry -> {
                    defaults.put(simpleConfigEntry.getKey(), simpleConfigEntry.getValue());
                });
                return SimpleConfig.writeWithReturn(defaults, CONFIG_FILE);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SimpleConfig getOrCreateClient(String modId, Set<SimpleConfigEntry<?>> references) {

        File CONFIG_DIR = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
        CONFIG_DIR.mkdirs();
        File CONFIG_FILE = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config" + File.separator + modId + "-client.toml");
        try {
            if (CONFIG_FILE.exists()) return read(CONFIG_FILE);
            else {
                Map<String, Object> defaults = new HashMap<>();
                references.forEach(simpleConfigEntry -> {
                    defaults.put(simpleConfigEntry.getKey(), simpleConfigEntry.getValue());
                });
                return SimpleConfig.writeWithReturn(defaults, CONFIG_FILE);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SimpleConfig getOrCreateDedicatedServer(String modId, Set<SimpleConfigEntry<?>> references) {

        File CONFIG_DIR = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
        CONFIG_DIR.mkdirs();
        File CONFIG_FILE = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config" + File.separator + modId + "-server.toml");
        try {
            if (CONFIG_FILE.exists()) return read(CONFIG_FILE);
            else {
                Map<String, Object> defaults = new HashMap<>();
                references.forEach(simpleConfigEntry -> {
                    defaults.put(simpleConfigEntry.getKey(), simpleConfigEntry.getValue());
                });
                return SimpleConfig.writeWithReturn(defaults, CONFIG_FILE);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(Map<String, ?> from, File target) throws IOException {
        SimpleConfigWriter.writeObjectToFile(from, target);
    }

    public static SimpleConfig writeWithReturn(Map<String, ?> from, File target) throws IOException {
        return SimpleConfigWriter.writeObjectToFileWithReturn(from, target);
    }

    public static SimpleConfig read(File file) {
        try {
            return attemptInputStreamReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SimpleConfig attemptInputStreamReader(Reader reader) {

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder w = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                w.append(line).append('\n');
                line = bufferedReader.readLine();
            }

            return getTomlFromString(w.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SimpleConfig getTomlFromString(String tomlString) throws IllegalStateException {

        SimpleConfig config = new SimpleConfig();

        Results results = SimpleConfigParser.run(tomlString);

        if (results.errors.hasErrors()) {
            throw new IllegalStateException(results.errors.toString());
        }

        config.values = results.consume();

        return config;
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public String getString(String key, String defaultValue) {
        String val = getString(key);
        return val == null ? defaultValue : val;
    }

    public Long getLong(String key) {
        return (Long) get(key);
    }

    public Long getLong(String key, Long defaultValue) {
        Long val = getLong(key);
        return val == null ? defaultValue : val;
    }

    public <T> List<T> getList(String key) {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) get(key);

        return list;
    }

    public <T> List<T> getList(String key, List<T> defaultValue) {
        List<T> list = getList(key);

        return list != null ? list : defaultValue;
    }

    public Boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        Boolean val = getBoolean(key);
        return val == null ? defaultValue : val;
    }

    public Date getDate(String key) {
        return (Date) get(key);
    }

    public Date getDate(String key, Date defaultValue) {
        Date val = getDate(key);
        return val == null ? defaultValue : val;
    }

    public Double getDouble(String key) {
        return (Double) get(key);
    }

    public Double getDouble(String key, Double defaultValue) {
        Double val = getDouble(key);
        return val == null ? defaultValue : val;
    }

    @SuppressWarnings("unchecked")
    public SimpleConfig getTable(String key) {
        Map<String, Object> map = (Map<String, Object>) get(key);

        return map != null ? new SimpleConfig(null, map) : null;
    }

    @SuppressWarnings("unchecked")
    public List<SimpleConfig> getTables(String key) {
        List<Map<String, Object>> tableArray = (List<Map<String, Object>>) get(key);

        if (tableArray == null) {
            return null;
        }

        ArrayList<SimpleConfig> tables = new ArrayList<SimpleConfig>();

        for (Map<String, Object> table : tableArray) {
            tables.add(new SimpleConfig(null, table));
        }

        return tables;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public boolean containsPrimitive(String key) {
        Object object = get(key);

        return object != null && !(object instanceof Map) && !(object instanceof List);
    }

    public boolean containsTable(String key) {
        Object object = get(key);

        return object != null && (object instanceof Map);
    }

    public boolean containsTableArray(String key) {
        Object object = get(key);

        return object != null && (object instanceof List);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @SuppressWarnings("unchecked")
    private Object get(String key) {

        if (values.containsKey(key)) {
            return values.get(key);
        }

        Object current = new HashMap<String, Object>(values);

        Keys.Key[] keys = Keys.split(key);

        for (Keys.Key checkedKey : keys) {

            if (checkedKey.index == -1 && current instanceof Map && ((Map<String, Object>) current).containsKey(checkedKey.path)) {
                return ((Map<String, Object>) current).get(checkedKey.path);
            }

            current = ((Map<String, Object>) current).get(checkedKey.name);

            if (checkedKey.index > -1 && current != null) {
                if (checkedKey.index >= ((List<?>) current).size()) return null;
                current = ((List<?>) current).get(checkedKey.index);
            }

            if (current == null) return defaults != null ? defaults.get(key) : null;
        }

        return current;
    }
}

