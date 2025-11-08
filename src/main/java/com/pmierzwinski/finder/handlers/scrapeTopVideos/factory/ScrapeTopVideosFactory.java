package com.pmierzwinski.finder.handlers.scrapeTopVideos.factory;

import com.pmierzwinski.finder.handlers.scrapeTopVideos.model.PageVideoModel;
import com.pmierzwinski.finder.modules.videos.repository.VideoEntity;

public class ScrapeTopVideosFactory {

    public static VideoEntity toVideoEntity(PageVideoModel video) {
        VideoEntity entity = new VideoEntity();
        entity.setName(video.getName());
        entity.setUrl(video.getUrl());
        return entity;
    }

}
