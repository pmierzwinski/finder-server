package com.pmierzwinski.finder.modules.extractor;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import lombok.Getter;

@Getter
public class Page {
    private final Config.GroupDefinition<VideoRow> videos = new Config.VideoGroupDefinition();
    private final Config.GroupDefinition<HeaderRow> headers = new Config.HeaderGroupDefinition();
}