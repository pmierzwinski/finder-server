package com.pmierzwinski.finder.handlers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.videos.VideosService;
import com.pmierzwinski.finder.modules.videos.model.VideoEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            Page page = scrapingService.scrapePage(pageConfig, Page.class);
            videosService.updateVideosFor(
                pageConfig.getId(),
                page.getVideos().stream().map(this::toEntity).toList()
            );
        });
    }


    public VideoEntity toEntity(ScrapeTopVideosHandler.Page.Video video) {
        VideoEntity entity = new VideoEntity();
        entity.setName(video.getName());
        entity.setUrl(video.getUrl());
        return entity;
    }

    @Getter @Setter
    public static class Page {
        private List<Video> videos;

        @Getter @Setter
        public static class Video {
            String name;
            String url;
        }
    }
}
