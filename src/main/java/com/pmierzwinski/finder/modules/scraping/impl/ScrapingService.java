package com.pmierzwinski.finder.modules.scraping.impl;

import com.pmierzwinski.finder.config.PageConfig;
import com.pmierzwinski.finder.lib.scrapi.ConfigBuilder;
import com.pmierzwinski.finder.lib.scrapi.Scrapi;
import com.pmierzwinski.finder.lib.scrapi.ScrapiPageConfig;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusEntity;
import com.pmierzwinski.finder.modules.scraping.model.PageModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapingService {

    private final ScrapingStatusComponent statusComponent;

    public ScrapingService(
            ScrapingStatusComponent statusComponent
    ) {
        this.statusComponent = statusComponent;
    }

    public PageModel scrape(PageConfig pageConfig) {
        statusComponent.startScraping(pageConfig.getId());
        try {
            var result = scrapePage(pageConfig);
            statusComponent.finishScraping(pageConfig.getId(), result.getVideos().size());
            return result;
        } catch (Exception ex) {
            statusComponent.failScraping(pageConfig.getId(), ex.getMessage());
            throw ex;
        }
    }

    public List<ScrapingStatusEntity> getLastScrapingStatuses() {
        return statusComponent.getLastStatuses();
    }

    public List<ScrapingStatusEntity> getLastScrapingStatuses(String site) {
        return statusComponent.getLastStatuses(site);
    }

    public PageModel scrapePage(ScrapiPageConfig config) {
        return Scrapi.scrape(
                new ConfigBuilder().fromScrapiConfig(config).build(),
                PageModel.class
        );
    }
}

