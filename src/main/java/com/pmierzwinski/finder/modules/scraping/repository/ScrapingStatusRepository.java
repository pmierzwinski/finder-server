package com.pmierzwinski.finder.modules.scraping.repository;

import com.pmierzwinski.finder.modules.scraping.model.ScrapingStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScrapingStatusRepository extends JpaRepository<ScrapingStatusEntity, Long> {
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

    ScrapingStatusEntity findTopByPageIdOrderByStartTimeDesc(String pageId);

    List<ScrapingStatusEntity> findTop10ByPageIdOrderByStartTimeDesc(String pageId);

}
