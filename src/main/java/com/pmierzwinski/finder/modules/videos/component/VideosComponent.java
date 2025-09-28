package com.pmierzwinski.finder.modules.videos.component;

import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import com.pmierzwinski.finder.modules.videos.repository.VideosRepository;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideosComponent {

    private final VideosRepository videosRepository;

    public VideosComponent(VideosRepository videosRepository) {
        this.videosRepository = videosRepository;
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
    public void updateTopVideos(Map<String, List<VideoRow>> newVideos) {
        newVideos.forEach((pageId, videos) -> {
            videosRepository.deleteByPage(pageId);
            videosRepository.saveAll(videos);
        });
    }
}
