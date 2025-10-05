package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.modules.extractor.components.ExtractDefinition;
import com.pmierzwinski.finder.modules.scraping.component.PageDefinition;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "scraper")
public class Config {

    /** Lista konfiguracji dla poszczególnych stron */
    private List<PageConfig> pages;

    @Getter @Setter
    public static class PageConfig {
        private PageDefinition pageDefinition;
        private ExtractDefinition scrapingObject;
    }
}
