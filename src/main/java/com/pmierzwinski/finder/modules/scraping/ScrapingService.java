package com.pmierzwinski.finder.modules.scraping;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.component.PageDefinition;
import com.pmierzwinski.finder.modules.scraping.component.PageId;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingComponent;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingStatusComponent;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import com.pmierzwinski.finder.modules.testing.ExamplePageConfig;
import com.pmierzwinski.finder.usage.PageConfig;
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

    public String getPageHtml(PageConfig definition) {
        try {
            onScrapingStarted(definition.getId());

            var html = scrapingComponent.scrapePageHtml(definition.dataUrl(), definition.verifySelector());

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


    private void onScrapingStarted(PageId pageId) {
        scrapingStatusComponent.onScrapingStarted(pageId);
    }

    private void onScrapingFinished(PageId pageId, int videoCount) {
        scrapingStatusComponent.finishSuccess(pageId, videoCount);
    }

    private void onScrapingFail(PageId pageId, String message) {
        scrapingStatusComponent.finishError(pageId, message);
    }
}
