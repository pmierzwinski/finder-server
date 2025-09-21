package com.example.finder.scraper;

import com.example.finder.config.SiteConfig;

public interface ScraperStrategy {
    void scrape(SiteConfig config) throws Exception;
    void setConfig(SiteConfig.Site config);
}
