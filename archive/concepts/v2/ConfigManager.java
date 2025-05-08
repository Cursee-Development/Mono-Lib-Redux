package com.cursee.monolib.core.util.config.v2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import com.google.gson.*;

public class ConfigManager {
    private final File file;
    private final ConfigObject config;

    public ConfigManager(File file, ConfigObject config) {
        this.file = file;
        this.config = config;
    }

    public void save() {

        JsonObject json = new JsonObject();
        try {
            for (ConfigValue<?> val : config.getValues().values()) {
                Object v = val.get();
                if (v instanceof Boolean) json.addProperty(val.getKey(), (Boolean) v);
                else if (v instanceof Number) json.addProperty(val.getKey(), (Number) v);
                else if (v instanceof String) json.addProperty(val.getKey(), (String) v);
                else if (v instanceof Iterable<?>) {
                    JsonArray array = new JsonArray();
                    for (Object item : (Iterable<?>) v) array.add(item.toString());
                    json.add(val.getKey(), array);
                }
            }
        }
        catch (Exception ignored) {}

        try (FileWriter writer = new FileWriter(file)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(json, writer);
        }
        catch (Exception ignored) {}
    }

    public void load() {
        if (!file.exists()) return;

        try {
            JsonObject json = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();

            for (ConfigValue<?> val : config.getValues().values()) {
                JsonElement el = json.get(val.getKey());
                if (el == null) continue;
                Object result = switch (val.getDefaultValue().getClass().getSimpleName()) {
                    case "String" -> el.getAsString();
                    case "Boolean" -> el.getAsBoolean();
                    case "Integer" -> el.getAsInt();
                    case "Float" -> el.getAsFloat();
                    case "Double" -> el.getAsDouble();
                    default -> null;
                };
                if (result != null) ((ConfigValue<Object>) val).set(result);
            }
        }
        catch (Exception ignored) {}
    }
}
