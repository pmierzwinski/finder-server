package com.pmierzwinski.finder.modules.videos.component;

import com.pmierzwinski.finder.modules.extractor.components.DataDefinition;
import com.pmierzwinski.finder.modules.extractor.components.FieldDefinition;
import com.pmierzwinski.finder.modules.scraping.component.PageDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoSelectorDefinition extends DataDefinition {



    public VideoSelectorDefinition(String groupSelector, List<FieldDefinition> definitions) {



        super(groupSelector, definitions);
    }
}