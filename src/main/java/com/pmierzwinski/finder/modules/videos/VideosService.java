package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.videos.component.VideosComponent;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideosService {

    private final VideosComponent videosComponent;

    public VideosService(VideosComponent videosComponent) {
        this.videosComponent = videosComponent;
    }

//    @Scheduled(fixedRate = 60000)
    public void updateTopVideos() {
        videosComponent.updateTopVideos();
    }

    public List<VideoRow> getAllVideos() {
        return videosComponent.getAllVideos();
    }

    public VideoRow getVideoById(Long id) {
        return videosComponent.getVideoById(id);
    }
}

