package com.pmierzwinski.finder.modules.videos.db;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter @NoArgsConstructor
public class VideoRow {
    private String title;
    private String imageUrl;

    @Override
    public String toString() {
        return "VideoRow{title='%s', imageUrl='%s'}".formatted(title, imageUrl);
    }
}