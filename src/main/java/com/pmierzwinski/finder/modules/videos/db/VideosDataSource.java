package com.pmierzwinski.finder.modules.videos.db;

import com.pmierzwinski.finder.modules.videos.model.Video;

import java.util.List;

public interface VideosDataSource {
    List<Video> findAll();

    void saveAll(List<Video> videos);

    void deleteByPage(String page);
}