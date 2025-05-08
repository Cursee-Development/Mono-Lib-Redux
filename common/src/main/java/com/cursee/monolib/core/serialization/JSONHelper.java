package com.cursee.monolib.core.serialization;

import com.google.gson.*;

/** Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>. */
public class JSONHelper {

    private static final Gson GSON = new GsonBuilder().create();

    public static JsonElement getAsElement(String jsonString) {

        return GSON.fromJson(jsonString, JsonElement.class);
    }

    public static String getAsString(JsonElement element) {

        if (element instanceof JsonPrimitive primitive && primitive.isString()) {

            return primitive.getAsString();
        }

        throw new JsonParseException("Expected String value but got " + element.toString());
    }
}
