package com.pmierzwinski.finder.modules.videos.component;

import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import com.pmierzwinski.finder.modules.videos.repository.VideosRepository;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
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

    //todo add counter of being in top
    //todo leave old videos but mark them as old
    public void updateTopVideos() {
        var videosMap = scrapingComponent.scrapeTopVideos();

        videosMap.forEach((pageId, videos) -> {
            videosRepository.deleteByPage(pageId);
            videosRepository.saveAll(videos);
        });
    }
}
