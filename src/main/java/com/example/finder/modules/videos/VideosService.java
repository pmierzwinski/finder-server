package com.example.finder.modules.videos;

import com.example.finder.modules.videos.component.VideosComponent;
import org.springframework.scheduling.annotation.Scheduled;
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

