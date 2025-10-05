package com.pmierzwinski.finder.modules.extractor.base;

import com.pmierzwinski.finder.modules.extractor.SelectorDefinition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseGroupDefinition {
    private SelectorDefinition groupSelector;
    abstract public BaseObjectDefinition getObjectDefinition();
}
