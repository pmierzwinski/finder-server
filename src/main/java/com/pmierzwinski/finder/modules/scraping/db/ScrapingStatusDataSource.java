package com.pmierzwinski.finder.modules.scraping.db;

import com.pmierzwinski.finder.modules.scraping.model.ScrapingStatus;

import java.util.List;

public interface ScrapingStatusDataSource {
    List<ScrapingStatus> findLatestStatusesPerPage();

    ScrapingStatus findTopByPageIdOrderByStartTimeDesc(String pageId);

    List<ScrapingStatus> findTop10ByPageIdOrderByStartTimeDesc(String pageId);
}
