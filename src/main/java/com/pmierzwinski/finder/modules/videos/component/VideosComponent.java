package com.pmierzwinski.finder.modules.videos.component;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.videos.mapper.VideoMapper;
import com.pmierzwinski.finder.modules.videos.domain.model.Video;
import com.pmierzwinski.finder.modules.videos.db.entity.VideoEntity;
import com.pmierzwinski.finder.modules.videos.db.repository.VideosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosComponent {

    private final VideosRepository videosRepository;
    private final VideoMapper mapper = VideoMapper.INSTANCE;

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
    public void updateTopVideosFor(String pageId, List<Video> videos) {

        var entities = videos.stream()
                .map(mapper::toEntity)
                .toList();


        videosRepository.deleteByPage(pageId);
        videosRepository.saveAll(entities);
    }
}
