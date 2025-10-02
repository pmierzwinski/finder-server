package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.videos.component.VideosComponent;
import com.pmierzwinski.finder.modules.videos.factory.VideoFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapeTopVideosHandler {

    private final VideosService videosService;
    private final ScrapingService scrapingComponent;

    public ScrapeTopVideosHandler(ScrapingService scrapingService, VideosService videosService) {
        this.scrapingComponent = scrapingService;
        this.videosService = videosService;
    }

    @GetMapping("/update")
//    @Scheduled(fixedRate = 60000)
    public void handle() {
        var videosCandidateMap = scrapingComponent.getConfigPagesHtml();
        videosService.updateTopVideos(videosCandidateMap);
    }

}
