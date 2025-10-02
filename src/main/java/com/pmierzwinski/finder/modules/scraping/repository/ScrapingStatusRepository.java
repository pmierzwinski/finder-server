package com.pmierzwinski.finder.modules.scraping.repository;

import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScrapingStatusRepository extends JpaRepository<ScrapingStatusRow, Long> {
    List<ScrapingStatusRow> findByPageOrderByStartTimeDesc(String page);
    List<ScrapingStatusRow> findTop10ByPageOrderByStartTimeDesc(String page);

    @Query("""
    SELECT s
    FROM ScrapingStatusRow s
    WHERE s.startTime = (
        SELECT MAX(s2.startTime)
        FROM ScrapingStatusRow s2
        WHERE s2.pageId = s.pageId
    )
    """)
    List<ScrapingStatusRow> findLatestStatusesPerPage();
}
