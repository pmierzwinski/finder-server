package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.modules.extractor.VideoGroupDefinition;
import com.pmierzwinski.finder.modules.scraping.component.PageDefinition;
import com.pmierzwinski.finder.modules.scraping.component.PageId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "scraper")
public class Config {

    private List<PageConfig> pages;

    @Getter @Setter
    public static class PageConfig {
        private String id;
        private String name;
        private GroupDefinition<?> videos;
        private GroupDefinition<?> headers;
    }

    @Getter @Setter
    public static class LibSelector {
        private String css;
        private String tag;
    }

    @Getter @Setter
    public static class GroupDefinition<T> {
        private String groupCss;
        private Map<String, LibSelector> fields;

        // dane wynikowe po parsowaniu
        private List<T> items = new ArrayList<>();
    }

}
