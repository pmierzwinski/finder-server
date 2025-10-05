package com.pmierzwinski.finder.modules.extractor;

import com.pmierzwinski.finder.modules.extractor.base.BaseGroupDefinition;
import com.pmierzwinski.finder.modules.extractor.base.BaseObjectDefinition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoGroupDefinition extends BaseGroupDefinition {
    private VideoDefinition video;

    @Override
    public BaseObjectDefinition getObjectDefinition() {
        return video;
    }
}
