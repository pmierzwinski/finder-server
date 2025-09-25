package com.example.finder.modules.scraping;

import com.example.finder.modules.scraping.component.ScrapingComponent;
import com.example.finder.modules.videos.db.VideoRow;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class ScrapingService {

    ScrapingComponent scrapingComponent;

    ScrapingService(ScrapingComponent scrapingComponent) {
        this.scrapingComponent = scrapingComponent;
    }

    public List<VideoRow> scrapeTopVideos() throws InterruptedException {
        return scrapingComponent.scrapeTopVideos();
    }
}
