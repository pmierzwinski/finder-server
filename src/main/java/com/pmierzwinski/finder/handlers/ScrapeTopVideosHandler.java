package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.testing.VideoRow;
import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.extractor.GenericPageExtractor;
import com.pmierzwinski.finder.modules.extractor.Page;
import com.pmierzwinski.finder.scrapi.Extractor;
import com.pmierzwinski.finder.usage.PageModel;
import com.pmierzwinski.finder.usage.VideoModel;
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
            PageModel page = (PageModel) Extractor.extract(pageHtml, pageConfig);
            List<VideoModel> videos = page.getVideos();
        });
    }

}
