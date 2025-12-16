package com.pmierzwinski.finder.app;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusMapper;
import com.pmierzwinski.finder.modules.scraping.impl.ScrapingService;
import com.pmierzwinski.finder.modules.videos.impl.VideosService;
import org.springframework.stereotype.Service;

@Service
public class UpdateTopVideosUseCase {

    private final ScrapingService scrapingService;
    private final VideosService videosService;
    private final ScrapingStatusMapper scrapingStatusMapper;

    public UpdateTopVideosUseCase(
            ScrapingService scrapingService,
            VideosService videosService,
            ScrapingStatusMapper scrapingStatusMapper
    ) {
        this.scrapingService = scrapingService;
        this.videosService = videosService;
        this.scrapingStatusMapper = scrapingStatusMapper;
    }

    public void handle(Config config) {
        config.getPages().forEach(pageConfig -> {
            var pageModel = scrapingService.scrape(pageConfig);
            var videos = scrapingStatusMapper.toVideoList(pageModel.getVideos());

            videosService.updatePageVideos(
                    pageConfig.getId(),
                    videos
            );
        });
    }
}
