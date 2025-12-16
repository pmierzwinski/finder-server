package com.pmierzwinski.finder.app;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.domain.ScrapingService;
import com.pmierzwinski.finder.modules.scraping.mapper.ScrapeMapper;
import com.pmierzwinski.finder.modules.videos.impl.VideosService;
import org.springframework.stereotype.Service;

@Service
public class UpdateTopVideosUseCase {

    private final ScrapingService scrapingService;
    private final VideosService videosService;
    private final ScrapeMapper scrapeMapper;

    public UpdateTopVideosUseCase(
            ScrapingService scrapingService,
            VideosService videosService,
            ScrapeMapper scrapeMapper
    ) {
        this.scrapingService = scrapingService;
        this.videosService = videosService;
        this.scrapeMapper = scrapeMapper;
    }

    public void handle(Config config) {
        config.getPages().forEach(pageConfig -> {
            var pageModel = scrapingService.scrape(pageConfig);
            var videos = scrapeMapper.toVideoList(pageModel.getVideos());

            videosService.updatePageVideos(
                    pageConfig.getId(),
                    videos
            );
        });
    }
}
