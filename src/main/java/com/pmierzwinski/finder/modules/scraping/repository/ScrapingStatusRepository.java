package com.pmierzwinski.finder.modules.scraping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScrapingStatusRepository extends JpaRepository<ScrapingStatusEntity, Long> {
    List<ScrapingStatusEntity> findByPageOrderByStartTimeDesc(String page);
    List<ScrapingStatusEntity> findTop10ByPageOrderByStartTimeDesc(String page);

    @Query("""
    SELECT s
    FROM ScrapingStatusEntity s
    WHERE s.startTime = (
        SELECT MAX(s2.startTime)
        FROM ScrapingStatusEntity s2
        WHERE s2.pageId = s.pageId
    )
    """)
    List<ScrapingStatusEntity> findLatestStatusesPerPage();
}
