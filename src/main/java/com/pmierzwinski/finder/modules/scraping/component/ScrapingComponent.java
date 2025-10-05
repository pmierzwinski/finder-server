package com.pmierzwinski.finder.modules.scraping.component;

import org.springframework.stereotype.Component;

@Component
public class ScrapingComponent {
    private final WebManager webManager;

    public ScrapingComponent(WebManager webManager) {
        this.webManager = webManager;
    }

    public String scrapePageHtml(String url, String verySelector) {
        return webManager.getSiteHtml(url, verySelector);
    }

}

