package com.pmierzwinski.finder.modules.scraping.component;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import com.pmierzwinski.finder.utils.PageId;
import com.pmierzwinski.finder.utils.WebManager;
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

    public Map<PageId, String> scrapeConfigPagesHtml() {
        Map<PageId, String> result = new HashMap<>();

        try (WebManager webManager = new WebManager()) {
            for (Config.PageConfig pageConfig : config.getPagesConfigs()) {
                result.put(pageConfig.getId(), scrapePageHtml(webManager, pageConfig));
            }
        }

        return result;
    }

    private String scrapePageHtml(WebManager webManager, Config.PageConfig pageConfig) {
        try {
            onScrapingStarted(pageConfig.getId());

            var html = webManager.getSiteHtml(pageConfig.getDataUrl());

            onScrapingFinished(pageConfig.getId(), 0);

            return html;
        } catch (Exception e) {
            onScrapingFail(pageConfig.getId(), e.getMessage());
            return "";
        }
    }

    private void onScrapingStarted(PageId pageId) {
        scrapingStatusComponent.onScrapingStarted(pageId);
    }

    private void onScrapingFinished(PageId pageId, int videoCount) {
        scrapingStatusComponent.finishSuccess(pageId, videoCount);
    }

    private void onScrapingFail(PageId pageId, String message) {
        scrapingStatusComponent.finishError(pageId, message);
    }

    private void test() {

        var html = "<html><body><div class='video'><a href='url1' title='title1'>desc1<img src='img1'></a></div><div class='video'><a href='url2' title='title2'>desc2<img src='img2'></a></div></body></html>";

        HtmlExtractor<VideoRow> htmlExtractor = new HtmlExtractor<>(VideoRow.class);


    }
}

