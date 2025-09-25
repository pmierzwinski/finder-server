package com.example.finder.modules.videos;

import com.example.finder.modules.videos.service.VideosService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VideosScheduler {

    private final VideosService videosService;

    public VideosScheduler(VideosService videosService) {
        this.videosService = videosService;
    }

    @Scheduled(fixedRate = 60000)
    public void clearExpiredSessions() throws InterruptedException {
        videosService.updateTopVideos();
    }
}

