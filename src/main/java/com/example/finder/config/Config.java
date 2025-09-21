package com.example.finder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "scraper")
public class Config {
    private List<Site> sites;

    @Getter @Setter
    public static class Site {
        private String id;
        private String name;
        private String domain;
        private String dataUrl;
        private Selector groupSelector;
        private Selector contentUrlSelector;
        private Selector titleSelector;
        private Selector descriptionSelector;
        private Selector imageSelector;
    }

    @Getter @Setter
    public static class Selector {
        private String css;
        private String tag;
    }
}
