package com.cursee.monolib.core.util.config;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** Provides static save/load methods of SimpleConfig instances. */
public class SimpleConfigIO {

    public static String formatValue(Object value) {

        if (value instanceof String s) {
            return "\"" + s.replace("\"", "\\\"") + "\"";
        }

        if (value instanceof List<?> list) {
            List<String> formatted = new ArrayList<>();
            for (Object item : list) {
                formatted.add(formatValue(item));
            }
            return "[" + String.join(", ", formatted) + "]";
        }

        return value.toString();
    }

    public static void save(@NotNull SimpleConfig config, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            Map<String, List<String>> sectioned = new TreeMap<>();

            for (String key : config.keys()) {
                String[] parts = key.split("\\.");
                String section = parts.length > 1 ? String.join(".", Arrays.copyOf(parts, parts.length - 1)) : "";
                String entry = parts[parts.length - 1] + " = " + formatValue(config.get(key));

                sectioned.computeIfAbsent(section, k -> new ArrayList<>()).add(entry);
            }

            for (Map.Entry<String, List<String>> entry : sectioned.entrySet()) {
                if (!entry.getKey().isEmpty()) {
                    writer.write("[" + entry.getKey() + "]");
                    writer.newLine();
                }

                for (String line : entry.getValue()) {
                    writer.write(line);
                    writer.newLine();
                    if (!line.startsWith("#")) writer.newLine();
                }

                writer.newLine();
            }
        }
        catch (IOException e) {
            throw new UncheckedIOException("Failed to save config to " + path, e);
        }
    }

    private static List<String> splitRespectingQuotes(String raw) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : raw.toCharArray()) {
            if (c == '\"') inQuotes = !inQuotes;
            if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) result.add(sb.toString().trim());
        return result;
    }

    public static Object parseValue(String raw) {

        if (raw.startsWith("[") && raw.endsWith("]")) {

            List<Object> list = new ArrayList<>();

            String inner = raw.substring(1, raw.length() - 1).trim();
            List<String> elements = splitRespectingQuotes(inner);
            for (String el : elements) {
                list.add(parseValue(el.trim()));
            }

            return list;
        }

        if (raw.startsWith("\"") && raw.endsWith("\"")) {
            return raw.substring(1, raw.length() - 1);
        }

        if (raw.equals("true") || raw.equals("false")) {
            return Boolean.parseBoolean(raw);
        }

        if (raw.contains(".")) {
            try {
                return Float.parseFloat(raw);
            }
            catch (NumberFormatException ignored) {}
        }

        try {
            return Integer.parseInt(raw);
        }
        catch (NumberFormatException ignored) {}

        throw new IllegalStateException("SimpleConfig failed to parse value: " + raw);
    }

    public static @NotNull SimpleConfig load(SimpleConfig config, Path path) {
        try {
            return load(config, Files.readAllLines(path, StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            throw new UncheckedIOException("Failed to load config from " + path, e);
        }
    }

    public static @NotNull SimpleConfig load(SimpleConfig config, List<String> tomlString) {
        List<String> currentPath = new ArrayList<>(); // Track nested section path

        for (String line : tomlString) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith("#")) continue;

            if (line.startsWith("[") && line.endsWith("]")) {
                String sectionHeader = line.substring(1, line.length() - 1);
                currentPath = new ArrayList<>(Arrays.asList(sectionHeader.split("\\.")));
            }
            else {
                int eqIdx = line.indexOf("=");
                if (eqIdx == -1) continue;

                String key = line.substring(0, eqIdx).trim();
                String rawValue = line.substring(eqIdx + 1).trim();

                String fullKey = String.join(".", currentPath) + "." + key;

                Object value = parseValue(rawValue);
                config.putEntry(fullKey, value, false);
            }
        }

        return config;
    }
}
