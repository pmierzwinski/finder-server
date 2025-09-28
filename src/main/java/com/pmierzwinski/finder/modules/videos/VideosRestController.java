package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideosRestController {
    private final VideosService videoService;

    public VideosRestController(VideosService videoService) {
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

    @GetMapping("/update")
    public String updateTopVideos() {
        videoService.updateTopVideos();
        return "ok";
    }
}