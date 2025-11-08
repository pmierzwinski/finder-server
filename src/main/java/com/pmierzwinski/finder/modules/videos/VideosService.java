package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.videos.component.VideosComponent;
import com.pmierzwinski.finder.modules.videos.repository.VideoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosService {

    private final VideosComponent videosComponent;

    public VideosService(VideosComponent videosComponent) {
        this.videosComponent = videosComponent;
    }

    public List<VideoEntity> getAllVideos() {
        return videosComponent.getAllVideos();
    }

    public VideoEntity getVideoById(Long id) {
        return videosComponent.getVideoById(id);
    }

    public void updateVideosFor(String pageId, List<VideoEntity> newVideos) {
        videosComponent.updateTopVideosFor(pageId, newVideos);
    }
}

