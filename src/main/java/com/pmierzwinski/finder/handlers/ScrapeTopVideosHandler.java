package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.lib.scrapi.ConfigBuilder;
import com.pmierzwinski.finder.lib.scrapi.Extractor;
import com.pmierzwinski.finder.modules.scraping.Page;
import com.pmierzwinski.finder.modules.videos.db.VideoModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScrapeTopVideosHandler {

    private final Config  config;
    private final ScrapingService scrapingService;

    public ScrapeTopVideosHandler(Config  config, ScrapingService scrapingService) {
        this.config = config;
        this.scrapingService = scrapingService;
    }

    @GetMapping("/update")
//    @Scheduled(fixedRate = 60000)
    public void handle() {
        config.getPages().forEach(pageConfig -> {
            String pageHtml = scrapingService.getPageHtml(pageConfig);

            var config = new ConfigBuilder()
                    .fromPageConfig(pageConfig)
                    .validate()
                    .build();

            Extractor parser = new Extractor();
            Page page = parser.tryParse(pageHtml, config, Page.class);

            List<VideoModel> videos = page.getVideos();
        });
    }

}
