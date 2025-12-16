package com.pmierzwinski.finder.modules.videos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String pageId;
    private String name;
    private String url;
    private String imgUrl;
}