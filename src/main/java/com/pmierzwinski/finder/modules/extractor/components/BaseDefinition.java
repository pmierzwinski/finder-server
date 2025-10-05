package com.pmierzwinski.finder.modules.extractor.components;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDefinition {

    public List<FieldDefinition> toFieldDefinitions() {
        List<FieldDefinition> result = new ArrayList<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value instanceof SelectorDefinition selector && selector.getCss() != null) {
                    result.add(new FieldDefinition(field.getName(), selector));
                }
            } catch (IllegalAccessException ignored) {}
        }

        return result;
    }
}
