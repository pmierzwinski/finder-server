package com.pmierzwinski.finder.modules.scraping.component;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.parsers.VideoParser;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScrapingComponent {
    private final ScrapingStatusComponent scrapingStatusComponent;
    private final Config config;

    public ScrapingComponent(ScrapingStatusComponent scrapingStatusComponent, Config config) {
        this.scrapingStatusComponent = scrapingStatusComponent;
        this.config = config;
    }

    public Map<String, List<VideoRow>> scrapeTopVideos() {
        Map<String, List<VideoRow>> result = new HashMap<>();

        try (WebManager webManager = new WebManager()) {
            for (Config.Page pageConfig : config.getPages()) {
                var videos = scrapePage(webManager, pageConfig);
                if(videos.isEmpty()) {
                    continue;
                }

                result.put(pageConfig.getId(), videos);
            }
        }

        return result;
    }

    private List<VideoRow> scrapePage(WebManager webManager, Config.Page pageConfig) {
        try {
            onScrapingStarted(pageConfig.getId());

            String html = webManager.getSiteHtml(pageConfig.getDataUrl());
            List<VideoRow> videos = VideoParser.extractVideos(html, pageConfig);

            onScrapingFinished(pageConfig.getId(), videos.size());
            return videos;
        } catch (Exception e) {
            onScrapingFail(pageConfig.getId(), e.getMessage());
            return List.of();
        }
    }

    private void onScrapingStarted(String pageId) {
        scrapingStatusComponent.onScrapingStarted(pageId);
    }

    private void onScrapingFinished(String pageId, int videoCount) {
        scrapingStatusComponent.finishSuccess(pageId, videoCount);
    }

    private void onScrapingFail(String pageId, String message) {
        scrapingStatusComponent.finishError(pageId, message);
    }
}

