package com.example.finder.modules.scraping.repository;

import com.example.finder.modules.scraping.db.ScrapingStatusRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapingStatusRepository extends JpaRepository<ScrapingStatusRow, Long> {
    List<ScrapingStatusRow> findByPageOrderByStartTimeDesc(String page);
    ScrapingStatusRow findTop1ByPageOrderByStartTimeDesc(String page);
}
