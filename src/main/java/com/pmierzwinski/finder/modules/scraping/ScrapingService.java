package com.pmierzwinski.finder.modules.scraping;

import com.pmierzwinski.finder.utils.PageId;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingComponent;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingStatusComponent;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public Map<PageId, String> getConfigPagesHtml() {
        return scrapingComponent.scrapeConfigPagesHtml();
    }

    public List<ScrapingStatusRow> getLastScrapingStatuses() {
        return scrapingStatusComponent.getLastSiteStatuses();
    }

    public List<ScrapingStatusRow> getLastScrapingStatuses(String site) {
        return scrapingStatusComponent.getLastSiteStatuses(site);
    }
}
