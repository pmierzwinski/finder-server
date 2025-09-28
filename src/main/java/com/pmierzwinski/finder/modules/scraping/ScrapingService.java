package com.pmierzwinski.finder.modules.scraping;

import com.pmierzwinski.finder.modules.scraping.candidate.VideoCandidate;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingComponent;
import com.pmierzwinski.finder.modules.scraping.component.ScrapingStatusComponent;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public Map<String, List<VideoCandidate>> scrapeTopVideos() {
        return scrapingComponent.scrapeTopVideos();
    }

    public List<ScrapingStatusRow> getLastScrapingStatuses() {
        return scrapingStatusComponent.getLastSiteStatuses();
    }

    public List<ScrapingStatusRow> getLastScrapingStatuses(String site) {
        return scrapingStatusComponent.getLastSiteStatuses(site);
    }
}
