package com.pmierzwinski.finder.modules.scraping.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "scraping_status")
@Getter
@Setter
public class ScrapingStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String message;

    private int videoCount;

    public void markRunning() {
        this.startTime = LocalDateTime.now();
        this.endTime = null;
        this.videoCount = 0;
        this.status = Status.RUNNING;
        this.message = "Scraping started";
    }

    public void markSuccess(int total) {
        this.endTime = LocalDateTime.now();
        this.videoCount = total;
        this.status = Status.SUCCESS;
        this.message = "Completed";
    }

    public void markFailed(String msg) {
        this.endTime = LocalDateTime.now();
        this.status = Status.FAILED;
        this.message = msg;
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}

