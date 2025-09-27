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


    public ScrapingStatusRow() {}

    public ScrapingStatusRow(String page) {
        this.startTime = LocalDateTime.now();
        this.status = Status.RUNNING;
        this.totalCount = 0;
        this.successCount = 0;
        this.failedCount = 0;

        this.page = page;
        this.message = "Scraping top videos...";
    }

    public void fail(String result) {
        this.status = Status.FAILED;
        this.endTime = LocalDateTime.now();
        this.message = result;
    }

    public void success(int total) {
        this.status = Status.SUCCESS;
        this.totalCount = total;
        this.endTime = LocalDateTime.now();
        this.message = "Update completed";
    }

    public class Status {
        public static final String RUNNING = "RUNNING";
        public static final String FAILED = "FAILED";
        public static final String SUCCESS = "SUCCESS";
    }

}
