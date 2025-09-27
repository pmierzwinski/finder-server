package com.pmierzwinski.finder.modules.scraping;

import com.pmierzwinski.finder.modules.scraping.component.ScrapingComponent;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingStatusComponent;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
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

    public List<VideoRow> scrapeTopVideos() throws InterruptedException {
        return scrapingComponent.scrapeTopVideos();
    }

    public List<ScrapingStatusRow> getPagesStatuses() {
        return scrapingStatusComponent.getPagesStatuses();
    }

    public List<ScrapingStatusRow> getTopPageStatuses(String page) {
        return scrapingStatusComponent.getTopPageStatuses(page);
    }
}
