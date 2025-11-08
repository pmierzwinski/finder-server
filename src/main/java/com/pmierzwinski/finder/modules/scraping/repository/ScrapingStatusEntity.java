package com.pmierzwinski.finder.modules.scraping.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "scraping_status")
@Setter
@Getter
public class ScrapingStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageId; // np. "youtube", "vimeo"

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status; // RUNNING, SUCCESS, FAILED
    private String message;

    private int totalCount;
    private int successCount;
    private int failedCount;


    public ScrapingStatusEntity() {}

    public ScrapingStatusEntity(String pageId) {
        this.startTime = LocalDateTime.now();
        this.status = Status.RUNNING;
        this.totalCount = 0;
        this.successCount = 0;
        this.failedCount = 0;

        this.pageId = pageId;
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

    public void empty() {
        this.status = Status.EMPTY;
        this.totalCount = 0;
        this.endTime = LocalDateTime.now();
        this.message = "No videos found";
    }

    public class Status {
        public static final String RUNNING = "RUNNING";
        public static final String FAILED = "FAILED";
        public static final String SUCCESS = "SUCCESS";
        public static final String EMPTY = "EMPTY";
    }

}
