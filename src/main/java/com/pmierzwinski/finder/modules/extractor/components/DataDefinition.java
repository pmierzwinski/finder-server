package com.pmierzwinski.finder.modules.extractor.components;

import lombok.Getter;

import java.util.List;

@Getter
public class DataDefinition {

    String groupSelector;
    List<FieldDefinition> definitions;

    public DataDefinition(String groupSelector, List<FieldDefinition> definitions) {
        this.groupSelector = groupSelector;
        this.definitions = definitions;
    }
}
