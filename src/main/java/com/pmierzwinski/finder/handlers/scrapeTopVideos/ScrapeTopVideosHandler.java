package com.pmierzwinski.finder.handlers.scrapeTopVideos;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.handlers.scrapeTopVideos.factory.ScrapeTopVideosFactory;
import com.pmierzwinski.finder.handlers.scrapeTopVideos.model.PageModel;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.videos.VideosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapeTopVideosHandler {

    private final Config  config;
    private final ScrapingService scrapingService;
    private final VideosService videosService;

    public ScrapeTopVideosHandler(Config  config, ScrapingService scrapingService, VideosService videosService) {
        this.config = config;
        this.scrapingService = scrapingService;
        this.videosService = videosService;
    }

    @GetMapping("/update")
//    @Scheduled(fixedRate = 60000)
    public void handle() {
        config.getPages().forEach(pageConfig -> {
            PageModel page = scrapingService.scrapePage(pageConfig);
            videosService.updateVideosFor(
                pageConfig.getId(),
                page.getVideos().stream().map(ScrapeTopVideosFactory::toVideoEntity).toList()
            );
        });
    }

}
