package com.pmierzwinski.finder.modules.scraping;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.lib.scrapi.ConfigBuilder;
import com.pmierzwinski.finder.lib.scrapi.Extractor;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingComponent;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingStatusComponent;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
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

    public Page scrapePage(Config.PageConfig pageConfig) {
        String pageHtml = this.getPageHtml(pageConfig);
        var config = new ConfigBuilder()
                .fromPageConfig(pageConfig)
                .validate()
                .build();
        return Extractor.tryParse(pageHtml, config, Page.class);
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

    public List<ScrapingStatusRow> getLastScrapingStatuses() {
        return scrapingStatusComponent.getLastSiteStatuses();
    }

    public List<ScrapingStatusRow> getLastScrapingStatuses(String site) {
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
