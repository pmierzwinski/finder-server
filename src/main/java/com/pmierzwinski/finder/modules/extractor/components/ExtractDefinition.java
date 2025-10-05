package com.pmierzwinski.finder.modules.extractor.components;

import lombok.Getter;

import java.util.List;

@Getter
public class ExtractDefinition {

    String groupSelector;
    List<FieldDefinition> definitions;


    public ExtractDefinition(String groupSelector, List<FieldDefinition> definitions) {
        this.groupSelector = groupSelector;
        this.definitions = definitions;
    }

}
