package com.pmierzwinski.finder.modules.scraping.components;

import com.pmierzwinski.finder.lib.scrapi.ScrapiPageConfig;
import com.pmierzwinski.finder.modules.scraping.domain.model.PageModel;
import com.pmierzwinski.finder.lib.scrapi.ConfigBuilder;
import com.pmierzwinski.finder.lib.scrapi.Scrapi;
import org.springframework.stereotype.Component;

@Component
public class ScrapingComponent {
    public PageModel scrapePage(ScrapiPageConfig config) {
        return Scrapi.scrape(
            new ConfigBuilder().fromScrapiConfig(config).build(),
            PageModel.class
        );
    }
}
