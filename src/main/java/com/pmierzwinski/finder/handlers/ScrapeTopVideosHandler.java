package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.extractor.Extractor;
import com.pmierzwinski.finder.modules.extractor.components.ExtractDefinition;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.extractor.components.FieldDefinition;
import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScrapeTopVideosHandler {

    private final VideosService videosService;
    private final ScrapingService scrapingService;
    private final Config config;

    public ScrapeTopVideosHandler(ScrapingService scrapingService, VideosService videosService, Config config) {
        this.scrapingService = scrapingService;
        this.videosService = videosService;
        this.config = config;
    }

    @GetMapping("/update")
//    @Scheduled(fixedRate = 60000)
    public void handle() {
        config.getPages().forEach(pageConfig -> {
            String pageHtml = scrapingService.getPageHtml(pageConfig.getPageDefinition());

            Extractor<VideoRow> extractor = new Extractor<>(VideoRow.class, pageConfig.getScrapingObject());
            List<VideoRow> videos = extractor.extract(pageHtml);

            videosService.updateVideosFor(pageConfig.getPageDefinition().getId(), videos);
        });
    }

}
