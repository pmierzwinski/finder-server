package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.extractor.GenericPageExtractor;
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
    private final VideosService videosService;
    private final ScrapingService scrapingService;
    private final GenericPageExtractor extractor;

    public ScrapeTopVideosHandler(Config  config, ScrapingService scrapingService, VideosService videosService, GenericPageExtractor extractor) {
        this.config = config;
        this.scrapingService = scrapingService;
        this.videosService = videosService;
        this.extractor = extractor;
    }

    @GetMapping("/update")
//    @Scheduled(fixedRate = 60000)
    public void handle() {
        config.getPages().forEach(pageConfig -> {
            String pageHtml = scrapingService.getPageHtml(pageConfig);

            var config = new ConfigBuilder()
                    .fromYml(yaml)
                    .validate()
                    .build();

            Extractor parser = new Extractor();
            Page page = parser.tryParse(pageHtml, config, Page.class);

            List<VideoModel> videos = page.getVideos();
        });
    }

}
