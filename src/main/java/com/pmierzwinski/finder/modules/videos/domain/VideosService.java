package com.pmierzwinski.finder.modules.videos.domain;

import com.pmierzwinski.finder.modules.videos.db.entity.VideoEntity;
import com.pmierzwinski.finder.modules.videos.db.repository.VideosRepository;
import com.pmierzwinski.finder.modules.videos.mapper.VideoMapper;
import com.pmierzwinski.finder.modules.videos.domain.model.Video;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosService {

    private final VideosRepository videosRepository;
    private final VideoMapper mapper;

    public VideosService(VideosRepository videosRepository, VideoMapper mapper) {
        this.videosRepository = videosRepository;
        this.mapper = mapper;
    }

    public List<Video> getAllVideos() {
        return mapper.toDomainList(videosRepository.findAll());
    }

    public Video getVideoById(Long id) {
        return mapper.toDomain(
                videosRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Not found: " + id))
        );
    }

    public void updateTopVideosFor(String pageId, List<Video> videos) {
        videosRepository.deleteByPage(pageId);
        videosRepository.saveAll(mapper.toEntityList(videos));
    }
}


