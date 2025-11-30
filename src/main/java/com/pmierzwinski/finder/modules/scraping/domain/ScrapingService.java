package com.pmierzwinski.finder.modules.scraping.domain;

import com.pmierzwinski.finder.config.PageConfig;
import com.pmierzwinski.finder.modules.scraping.domain.model.PageModel;
import com.pmierzwinski.finder.modules.scraping.components.ScrapingComponent;
import com.pmierzwinski.finder.modules.scraping.components.ScrapingStatusComponent;
import com.pmierzwinski.finder.modules.scraping.db.entity.ScrapingStatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapingService {

    private final ScrapingComponent scrapingComponent;
    private final ScrapingStatusComponent statusComponent;

    public ScrapingService(
            ScrapingComponent scrapingComponent,
            ScrapingStatusComponent statusComponent
    ) {
        this.scrapingComponent = scrapingComponent;
        this.statusComponent = statusComponent;
    }

    public PageModel scrape(PageConfig pageConfig) {
        statusComponent.startScraping(pageConfig.getId());
        try {
            var result = scrapingComponent.scrapePage(pageConfig);
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
}

