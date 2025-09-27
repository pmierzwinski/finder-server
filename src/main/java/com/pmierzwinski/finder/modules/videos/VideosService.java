package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.videos.component.VideosComponent;
import org.springframework.stereotype.Component;

@Component
public class VideosService {

    private final VideosComponent videosComponent;

    public VideosService(VideosComponent videosComponent) {
        this.videosComponent = videosComponent;
    }

//    @Scheduled(fixedRate = 60000)
    public void updateTopVideos() throws InterruptedException {
        videosComponent.updateTopVideos();
    }
}

