package com.pmierzwinski.finder.modules.extractor;

import com.pmierzwinski.finder.modules.extractor.base.BaseObjectDefinition;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VideoDefinition extends BaseObjectDefinition {
    private SelectorDefinition title;
    private SelectorDefinition url;
    private SelectorDefinition thumbnail;
    private SelectorDefinition channel;
    private SelectorDefinition viewers;
}