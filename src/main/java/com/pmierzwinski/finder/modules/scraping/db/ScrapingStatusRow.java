package com.pmierzwinski.finder.modules.scraping.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "scraping_status")
@Setter
@Getter
public class ScrapingStatusRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String page; // np. "youtube", "vimeo"

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status; // RUNNING, SUCCESS, FAILED
    private String message;

    private int totalCount;
    private int successCount;
    private int failedCount;
}
