package com.pmierzwinski.finder.newIdea;

import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import lombok.Getter;

import java.util.List;

@Getter
public class Page {
    private final String name;
    private final List<VideoRow> videos;
    private final List<HeaderRow> headers;

    public Page(String name, List<VideoRow> videos, List<HeaderRow> headers) {
        this.name = name;
        this.videos = videos;
        this.headers = headers;
    }
}
