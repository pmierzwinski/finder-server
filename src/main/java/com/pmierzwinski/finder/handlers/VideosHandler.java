package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
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
    public List<VideoRow> getAllVideos() {
        return videoService.getAllVideos();
    }

    //todo @tracking annotation
    @GetMapping("/{id}")
    public VideoRow getVideo(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }

}
