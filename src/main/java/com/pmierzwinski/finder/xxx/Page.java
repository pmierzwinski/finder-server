package com.pmierzwinski.finder.xxx;

import lombok.Getter;

@Getter
public class Page {

    private GroupDefinition<VideoRow> videos = new GroupDefinition<>();
    private GroupDefinition<HeaderRow> headers = new GroupDefinition<>();

}
