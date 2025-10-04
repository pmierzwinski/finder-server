package com.pmierzwinski.finder.modules.scraping.component;

import java.lang.reflect.Field;
import java.util.Map;

public class ObjectMapper {

    public static <T> T mapToObject(Map<String, String> values, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Map.Entry<String, String> entry : values.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue();

                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);

                    Object converted = convert(value, field.getType());
                    field.set(instance, converted);

                } catch (NoSuchFieldException e) {
                    // jeśli model nie ma takiego pola – pomijamy
                }
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Mapping failed", e);
        }
    }

    private static Object convert(String value, Class<?> type) {
        if (type == String.class) return value;
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        return value;
    }
}
