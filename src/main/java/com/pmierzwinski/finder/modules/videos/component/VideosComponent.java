package com.pmierzwinski.finder.modules.videos.component;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import com.pmierzwinski.finder.modules.videos.repository.VideosRepository;
import com.pmierzwinski.finder.modules.scraping.component.PageId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosComponent {

    private final VideosRepository videosRepository;
    private final Config config;

    public VideosComponent(VideosRepository videosRepository, Config config) {
        this.videosRepository = videosRepository;
        this.config = config;
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
    public void updateTopVideosFor(PageId pageId, List<VideoRow> newVideos) {
        videosRepository.deleteByPage(pageId.getValue());
        videosRepository.saveAll(newVideos);
    }
}
