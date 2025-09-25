package com.example.finder.modules.videos.service;

import com.example.finder.modules.videos.db.VideoRow;
import com.example.finder.modules.videos.repository.VideosRepository;
import com.example.finder.utils.ScrapingComponent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosService {

    private final VideosRepository videosRepository;
    private final ScrapingComponent scrapingComponent;

    public VideosService(VideosRepository videosRepository, ScrapingComponent scrapingComponent) {
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


        videosRepository.deleteAll();//todo - usun te ktore sa dobrze pobrane
        videosRepository.saveAll(videos);
    }
}
