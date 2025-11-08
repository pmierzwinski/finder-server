package com.pmierzwinski.finder.modules.videos.component;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.videos.repository.VideoEntity;
import com.pmierzwinski.finder.modules.videos.repository.VideosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosComponent {

    private final VideosRepository videosRepository;

    public VideosComponent(VideosRepository videosRepository, Config config) {
        this.videosRepository = videosRepository;
    }

    public List<VideoEntity> getAllVideos() {
        return videosRepository.findAll();
    }

    public VideoEntity getVideoById(Long id) {
        return videosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
    }

    //todo add counter of being in top
    //todo leave old videos but mark them as old
    public void updateTopVideosFor(String pageId, List<VideoEntity> newVideos) {
        videosRepository.deleteByPage(pageId);
        videosRepository.saveAll(newVideos);
    }
}
