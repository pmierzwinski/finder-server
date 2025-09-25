package com.example.finder.modules.videos;

import com.example.finder.modules.videos.db.VideoRow;
import com.example.finder.modules.videos.component.VideosComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideosRestController {
    private final VideosComponent videosComponent;

    public VideosRestController(VideosComponent videoService) {
        this.videosComponent = videoService;
    }

    @GetMapping
    public List<VideoRow> getAllVideos() {
        return videosComponent.getAllVideos();
    }

    //todo @tracking annotation
    @GetMapping("/{id}")
    public VideoRow getVideo(@PathVariable Long id) {
        return videosComponent.getVideoById(id);
    }

    @GetMapping("/update")
    public String updateTopVideos() throws InterruptedException {
        videosComponent.updateTopVideos();
        return "ok";
    }
}