package com.example.finder.config;

import com.example.finder.model.VerificationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "scraper")
public class SiteConfig {
    private List<Site> sites;

    @Getter @Setter
    public static class Site {
        private String id;
        private String name;
        private String url;
        private String videoGroup;
        private String urlSelector;
        private String titleSelector;
        private String descriptionSelector;
        private String imageSelector;
        private String verificationCondition;
        private String verificationSelector;
    }
}
