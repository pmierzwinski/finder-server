package com.pmierzwinski.finder.modules.scraping;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.lib.scrapi.ConfigBuilder;
import com.pmierzwinski.finder.lib.scrapi.Extractor;
import com.pmierzwinski.finder.modules.scraping.components.ScrapingComponent;
import com.pmierzwinski.finder.modules.scraping.components.ScrapingStatusComponent;
import com.pmierzwinski.finder.modules.scraping.model.ScrapingStatusEntity;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter//todo is it needed?
public class ScrapingService {

    ScrapingComponent scrapingComponent;
    ScrapingStatusComponent scrapingStatusComponent;

    ScrapingService(
            ScrapingComponent scrapingComponent,
            ScrapingStatusComponent scrapingStatusComponent
    ) {
        this.scrapingComponent = scrapingComponent;
        this.scrapingStatusComponent = scrapingStatusComponent;
    }

    public <T> T scrapePage(Config.PageConfig pageConfig, Class<T> clazz) {
        String pageHtml = this.getPageHtml(pageConfig);

        var config = new ConfigBuilder()
                .fromPageConfig(pageConfig)
                .validate()
                .build();
        return Extractor.tryParse(pageHtml, config, clazz);
    }

    private String getPageHtml(Config.PageConfig definition) {
        try {
            onScrapingStarted(definition.getId());

            var html = scrapingComponent.scrapePageHtml(definition.getDataUrl(), definition.verifySelector());

            onScrapingFinished(definition.getId(), 0);

            return html;
        } catch (Exception e) {
            onScrapingFail(definition.getId(), e.getMessage());
            return "";
        }
    }

    public List<ScrapingStatusEntity> getLastScrapingStatuses() {
        return scrapingStatusComponent.getLastSiteStatuses();
    }

    public List<ScrapingStatusEntity> getLastScrapingStatuses(String site) {
        return scrapingStatusComponent.getLastSiteStatuses(site);
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
