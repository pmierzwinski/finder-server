package com.pmierzwinski.finder.modules.scraping.components;

import com.pmierzwinski.finder.lib.scrapi.ScrapiCssSelector;
import com.pmierzwinski.finder.lib.scrapi.WebManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScrapingComponent {
    private final WebManager webManager;

    public ScrapingComponent(WebManager webManager) {
        this.webManager = webManager;
    }

    public String scrapePageHtml(String url, List<ScrapiCssSelector> verySelectors) {
        return webManager.getSiteHtml(url, verySelectors);
    }

}

