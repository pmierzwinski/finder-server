package com.pmierzwinski.finder.adapters.rest;

import com.pmierzwinski.finder.modules.videos.domain.VideosService;
import com.pmierzwinski.finder.modules.videos.db.entity.VideoEntity;
import com.pmierzwinski.finder.modules.videos.domain.model.Video;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideosHandler {
    private final VideosService videoService;

    public VideosHandler(VideosService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<Video> getAll() {
        return videoService.getAllVideos();
    }
}
