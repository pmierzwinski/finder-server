package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.extractor.Extractor;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScrapeTopVideosHandler {

    private final Config config;
    private final VideosService videosService;
    private final ScrapingService scrapingService;

    public ScrapeTopVideosHandler(Config config, ScrapingService scrapingService, VideosService videosService) {
        this.config = config;
        this.scrapingService = scrapingService;
        this.videosService = videosService;
    }

    @GetMapping("/update")
//    @Scheduled(fixedRate = 60000)
    public void handle() {
        config.getPages().forEach(pageConfig -> {
            String pageHtml = scrapingService.getPageHtml(pageConfig.getPageDefinition());

            List<VideoRow> videos = Extractor.extract(pageHtml, VideoRow.class, pageConfig.getVideosDefinition());

            videosService.updateVideosFor(pageConfig.getPageDefinition().id(), videos);
        });
    }

}
