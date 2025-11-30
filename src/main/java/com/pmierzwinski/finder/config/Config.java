package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.modules.scraping.config.PageConfig;
import lombok.Data;
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
}
