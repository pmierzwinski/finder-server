package com.pmierzwinski.finder.modules.extractor;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import lombok.Getter;

import java.util.List;

@Getter
public class Page {
    private final Config.GroupDefinition<VideoRow> videos = new Config.VideoGroupDefinition();
    private final Config.GroupDefinition<HeaderRow> headers = new Config.HeaderGroupDefinition();

    public List<VideoRow> getVideos() {
        return videos.getItems();
    }

    public List<HeaderRow> getHeaders() {
        return headers.getItems();
    }
}