package com.pmierzwinski.finder.modules.extractor.components;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VideoDefinition extends BaseDefinition {
    private SelectorDefinition title;
    private SelectorDefinition url;
    private SelectorDefinition thumbnail;
    private SelectorDefinition channel;
    private SelectorDefinition viewers;
}