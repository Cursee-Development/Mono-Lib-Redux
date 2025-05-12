package com.cursee.monolib.core.function.example.wrapper;

import com.cursee.monolib.core.function.MapWrapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapWrapperExample {

    public static final Map<String, Integer> INT_BY_STRING_MAP = new LinkedHashMap<String, Integer>();

    static {
        INT_BY_STRING_MAP.put("valueOne", 1);
        INT_BY_STRING_MAP.put("valueTwo", 128);
        INT_BY_STRING_MAP.put("valueThree", 69);
        INT_BY_STRING_MAP.put("valueFour", 42);
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {

        /// With the possibility of overrides
        MapWrapper<String, Integer> wrapper = new MapWrapper<String, Integer>() {
            @Override
            public Map<String, Integer> root() {
                return INT_BY_STRING_MAP;
            }
        };

        /// Verbose definition
        wrapper = (MapWrapper<String, Integer>) () -> INT_BY_STRING_MAP;

        /// Simplified to basic lambda
        wrapper = () -> INT_BY_STRING_MAP;

        wrapper.forAllValues(i -> System.out.println("squared: " + (i * i)));
        wrapper.forAllEntries((s, i) -> System.out.println(s + " " + String.valueOf(i)));
    }
}
