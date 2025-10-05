package com.pmierzwinski.finder.modules.extractor.base;

import com.pmierzwinski.finder.modules.extractor.SelectorDefinition;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public abstract class BaseDefinition {
    private String groupSelector;

    @PostConstruct
    public void validate() {
        if (groupSelector == null || groupSelector.isBlank()) {
            throw new IllegalStateException(getClass().getSimpleName() + " missing groupSelector");
        }

        if (getSelectors().isEmpty()) {
            throw new IllegalStateException(getClass().getSimpleName() + " has no valid selectors defined");
        }
    }

    public Map<String, SelectorDefinition> getSelectors() {
        Map<String, SelectorDefinition> map = new HashMap<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.getType().equals(SelectorDefinition.class)) continue;
            field.setAccessible(true);
            try {
                SelectorDefinition selector = (SelectorDefinition) field.get(this);
                if (selector != null && selector.getCss() != null && !selector.getCss().isBlank()) {
                    map.put(field.getName(), selector);
                }
            } catch (IllegalAccessException ignored) {}
        }

        return map;
    }
}