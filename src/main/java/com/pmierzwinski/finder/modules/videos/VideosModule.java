package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.videos.model.Video;

import java.util.List;

public interface VideosModule {

    List<Video> getAllVideos();

    void updatePageVideos(String pageId, List<Video> videos);
}