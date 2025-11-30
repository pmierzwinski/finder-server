package com.pmierzwinski.finder.modules.videos.domain;

import com.pmierzwinski.finder.modules.videos.db.repository.VideosRepository;
import com.pmierzwinski.finder.modules.videos.mapper.VideoMapper;
import com.pmierzwinski.finder.modules.videos.domain.model.Video;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosService {

    private final VideosRepository repository;
    private final VideoMapper mapper;

    public VideosService(
            VideosRepository repository,
            VideoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Video> getAllVideos() {
        return mapper.toDomainList(repository.findAll());
    }

    public void updatePageVideos(String pageId, List<Video> videos) {
        videos.forEach(video -> video.setPageId(pageId));

        var entities = mapper.toEntityList(videos);
        repository.saveAll(entities);
    }
}

