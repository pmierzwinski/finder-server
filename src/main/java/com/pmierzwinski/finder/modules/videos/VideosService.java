package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.videos.component.VideosComponent;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import com.pmierzwinski.finder.modules.scraping.component.PageId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideosService {

    private final VideosComponent videosComponent;

    public VideosService(VideosComponent videosComponent) {
        this.videosComponent = videosComponent;
    }

    public List<VideoRow> getAllVideos() {
        return videosComponent.getAllVideos();
    }

    public VideoRow getVideoById(Long id) {
        return videosComponent.getVideoById(id);
    }

    public void updateTopVideos(Map<PageId, String> newVideos) {
        videosComponent.updateTopVideos(newVideos);
    }

    public void updateVideosFor(PageId pageId, List<VideoRow> newVideos) {
        videosComponent.updateTopVideosFor(pageId, newVideos);
    }
}

