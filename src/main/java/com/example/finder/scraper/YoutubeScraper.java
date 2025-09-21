package com.example.finder.scraper;

import com.example.finder.annotation.Scraper;
import com.example.finder.config.SiteConfig;
import com.example.finder.model.ScrapedItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Scraper("youtube")
@Component
public class YoutubeScraper extends BaseScraper implements ScraperStrategy {

    @Override
    public void scrape(SiteConfig config) throws Exception {




        return List.of();

    }

    public Boolean shouldVerify() {
        return getDriver().getCurrentUrl().contains(getConfig().getVerificationCondition());
    }
}
