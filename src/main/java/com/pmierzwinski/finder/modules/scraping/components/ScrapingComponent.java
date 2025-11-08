package com.pmierzwinski.finder.modules.scraping.components;

import com.pmierzwinski.finder.handlers.scrapeTopVideos.config.PageConfig;
import com.pmierzwinski.finder.handlers.scrapeTopVideos.model.PageModel;
import com.pmierzwinski.finder.lib.scrapi.ConfigBuilder;
import com.pmierzwinski.finder.lib.scrapi.Extractor;
import org.springframework.stereotype.Component;


@Component
public class ScrapingComponent {
    public PageModel scrapePage(PageConfig pageConfig) {
        var config = new ConfigBuilder()
                .fromPageConfig(pageConfig)
                .validate()
                .build();
        return Extractor.scrapePage(config, PageModel.class);
    }
}
