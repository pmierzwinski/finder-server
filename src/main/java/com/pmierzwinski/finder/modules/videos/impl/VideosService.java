package com.pmierzwinski.finder.modules.videos.impl;

import com.pmierzwinski.finder.modules.videos.VideosModule;
import com.pmierzwinski.finder.modules.videos.db.VideosDataSource;
import com.pmierzwinski.finder.modules.videos.model.Video;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosService implements VideosModule {

    private final VideosDataSource repository;

    VideosService(
            VideosDataSource repository
    ) {
        this.repository = repository;
    }

    @Override
    public List<Video> getAllVideos() {
        return repository.findAll();
    }

    @Override
    public void updatePageVideos(String pageId, List<Video> videos) {
        videos.forEach(video -> video.setPageId(pageId));
        repository.saveAll(videos);
    }
}
