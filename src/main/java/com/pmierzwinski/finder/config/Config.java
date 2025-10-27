package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.lib.scrapi.ScrapiCssSelector;
import com.pmierzwinski.finder.lib.scrapi.ScrapiGroupSelector;
import com.pmierzwinski.finder.lib.scrapi.ScrapiPageConfig;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "scraper")
@Validated
@Data
public class Config {
    private List<PageConfig> pages;


    @Getter @Setter
    public static class PageConfig extends ScrapiPageConfig {
        ScrapiGroupSelector<VideoModelConfig> videos;
    }

    @Getter @Setter
    public static class VideoModelConfig {//todo maybe it should be in VideoModel - but maybe not
        ScrapiCssSelector name;
        ScrapiCssSelector url;
    }
}
