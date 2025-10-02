package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.utils.PageId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "scraper")
public class Config {

    private List<PageConfig> pagesConfigs;

    @Getter @Setter
    public static class PageConfig {
        private PageId id;
        private String name;
        private String domain;
        private String dataUrl;
        private VideoSelector videoSelector;
        private ValidationSelector validationSelector;
    }

    @Getter @Setter
    public static class VideoSelector {
        private GroupSelector group;
        private AttributeSelector url;
        private AttributeSelector title;
        private AttributeSelector description;
        private AttributeSelector image;
    }

    @Getter @Setter
    public static class ValidationSelector {
        private GroupSelector group;
        private AttributeSelector pick;
    }

    @Getter @Setter
    public static class AttributeSelector extends GroupSelector {
        private String attribute;
    }

    @Getter @Setter
    public static class GroupSelector {
        private String css;
    }
}
