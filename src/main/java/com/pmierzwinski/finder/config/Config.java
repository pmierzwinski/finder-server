package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.modules.extractor.VideoDefinition;
import com.pmierzwinski.finder.modules.scraping.component.PageDefinition;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "scraper")
public class Config {

    private List<PageConfig> pages;

    @Getter @Setter
    public static class PageConfig {
        private PageDefinition pageDefinition;
        private VideoDefinition videosDefinition;
    }

}
