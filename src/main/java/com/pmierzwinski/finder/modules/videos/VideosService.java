package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.videos.component.VideosComponent;
import com.pmierzwinski.finder.modules.videos.mapper.VideoMapper;
import com.pmierzwinski.finder.modules.videos.model.Video;
import com.pmierzwinski.finder.modules.videos.model.VideoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideosService {

    private final VideosComponent videosComponent;
    private final VideoMapper mapper = VideoMapper.INSTANCE;

    public VideosService(VideosComponent videosComponent) {
        this.videosComponent = videosComponent;
    }

    public List<VideoEntity> getAllVideos() {
        return videosComponent.getAllVideos();
    }

    public VideoEntity getVideoById(Long id) {
        return videosComponent.getVideoById(id);
    }

    public void updatePageVideos(String pageId, List<Video> videos) {
        videosComponent.updateTopVideosFor(pageId, videos);
    }
}

