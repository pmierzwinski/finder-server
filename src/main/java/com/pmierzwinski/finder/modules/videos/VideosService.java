package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.videos.component.VideosComponent;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import com.pmierzwinski.finder.modules.videos.factory.VideoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideosService {

    private final VideosComponent videosComponent;
    private final ScrapingService scrapingComponent;

    public VideosService(ScrapingService scrapingComponent, VideosComponent videosComponent) {
        this.videosComponent = videosComponent;
        this.scrapingComponent = scrapingComponent;
    }

//    @Scheduled(fixedRate = 60000)
    public void updateTopVideos() {
        var videosCandidateMap = scrapingComponent.scrapeTopVideos();

        var videosMap = videosCandidateMap.entrySet().stream().collect(
                java.util.stream.Collectors.toMap(
                        java.util.Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(VideoFactory::fromCandidate).toList()
                )
        );

        videosComponent.updateTopVideos(videosMap);
    }

    public List<VideoRow> getAllVideos() {
        return videosComponent.getAllVideos();
    }

    public VideoRow getVideoById(Long id) {
        return videosComponent.getVideoById(id);
    }
}

