package com.pmierzwinski.finder.handlers.getVideos;

import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.videos.repository.VideoEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class GetVideosHandler {
    private final VideosService videoService;

    public GetVideosHandler(VideosService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<VideoEntity> getAllVideos() {
        return videoService.getAllVideos();
    }

    //todo @tracking annotation
    @GetMapping("/{id}")
    public VideoEntity getVideo(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }

}
