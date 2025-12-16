package com.pmierzwinski.finder.adapters.rest;

import com.pmierzwinski.finder.modules.videos.VideosModule;
import com.pmierzwinski.finder.modules.videos.model.Video;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideosHandler {
    private final VideosModule videosModule;

    public VideosHandler(VideosModule videosModule) {
        this.videosModule = videosModule;
    }

    @GetMapping
    public List<Video> getAll() {
        return videosModule.getAllVideos();
    }
}
