package com.example.finder.modules.videos.component;

import com.example.finder.modules.videos.db.VideoRow;
import com.example.finder.modules.videos.repository.VideosRepository;
import com.example.finder.modules.scraping.ScrapingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosComponent {

    private final VideosRepository videosRepository;
    private final ScrapingService scrapingComponent;

    public VideosComponent(VideosRepository videosRepository, ScrapingService scrapingComponent) {
        this.videosRepository = videosRepository;
        this.scrapingComponent = scrapingComponent;
    }

    public List<VideoRow> getAllVideos() {
        return videosRepository.findAll();
    }

    public VideoRow getVideoById(Long id) {
        return videosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
    }

    public void updateTopVideos() throws InterruptedException {
        var videos = scrapingComponent.scrapeTopVideos();

        videosRepository.deleteAll();
        videosRepository.saveAll(videos);
    }
}
