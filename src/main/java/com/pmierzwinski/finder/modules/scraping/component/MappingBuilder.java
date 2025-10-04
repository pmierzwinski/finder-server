package com.pmierzwinski.finder.modules.scraping.component;

import java.util.ArrayList;
import java.util.List;

public class MappingBuilder {
    private final List<FieldDefinition> definitions = new ArrayList<>();

    public MappingBuilder add(String fieldName, String cssSelector) {
        definitions.add(new FieldDefinition(fieldName, cssSelector));
        return this;
    }

    public List<FieldDefinition> build() {
        return definitions;
    }
}
