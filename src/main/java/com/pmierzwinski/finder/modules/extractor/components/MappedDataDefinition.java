package com.pmierzwinski.finder.modules.extractor.components;

import java.util.List;
import java.util.Map;

public abstract class MappedDataDefinition extends DataDefinition {
    protected abstract Map<String, String> getFieldMap();

    @Override
    public List<FieldDefinition> getDefinitions() {
        return getFieldMap().entrySet().stream()
                .map(e -> new FieldDefinition(e.getKey(), e.getValue(), null))
                .toList();
    }
}