package com.example.finder.service;

import com.example.finder.model.ScrapedItem;
import com.example.finder.scraper.ScraperStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScraperManager {

    private final Map<String, ScraperStrategy> strategies;

    public ScraperManager(List<ScraperStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ScraperStrategy::getSiteName, s -> s));
    }

    public List<ScrapedItem> scrape(String site, int limit, boolean clickConsent) throws Exception {
        ScraperStrategy strategy = strategies.get(site);
        if (strategy == null) {
            throw new IllegalArgumentException("No scraper for site: " + site);
        }
        return strategy.scrape(limit, clickConsent);
    }
}
