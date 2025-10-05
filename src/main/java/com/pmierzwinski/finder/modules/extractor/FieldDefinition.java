package com.pmierzwinski.finder.modules.extractor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDefinition {
    private String name;
    private SelectorDefinition selector;
}