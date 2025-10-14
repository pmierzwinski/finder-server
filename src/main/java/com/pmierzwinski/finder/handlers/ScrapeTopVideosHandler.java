package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.extractor.GenericPageExtractor;
import com.pmierzwinski.finder.modules.extractor.Page;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScrapeTopVideosHandler {

    private final Config config;
    private final VideosService videosService;
    private final ScrapingService scrapingService;
    private final GenericPageExtractor extractor;

    public ScrapeTopVideosHandler(Config config, ScrapingService scrapingService, VideosService videosService, GenericPageExtractor extractor) {
        this.config = config;
        this.scrapingService = scrapingService;
        this.videosService = videosService;
        this.extractor = extractor;
    }

    @GetMapping("/update")
//    @Scheduled(fixedRate = 60000)
    public void handle() {
        config.getPages().forEach(pageConfig -> {

            //to one method
            String pageHtml = scrapingService.getPageHtml(pageConfig);
            Page page = extractor.parse(pageConfig, pageHtml);

            List<VideoRow> videos = page.getVideos().getItems();
            videosService.updateVideosFor(pageConfig.getId(), videos);
        });
    }

}
