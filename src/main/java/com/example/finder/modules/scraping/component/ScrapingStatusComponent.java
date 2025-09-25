package com.example.finder.modules.scraping.component;

import com.example.finder.modules.scraping.db.ScrapingStatusRow;
import com.example.finder.modules.scraping.repository.ScrapingStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScrapingStatusComponent {

    private final ScrapingStatusRepository repository;

    public ScrapingStatusComponent(ScrapingStatusRepository repository) {
        this.repository = repository;
    }

    public ScrapingStatusRow start(String page, String message) {
        ScrapingStatusRow status = new ScrapingStatusRow();
        status.setPage(page);
        status.setStartTime(LocalDateTime.now());
        status.setStatus("RUNNING");
        status.setMessage(message);
        return repository.save(status);
    }

    public void finishSuccess(ScrapingStatusRow status, int total, int success, int failed, String message) {
        status.setEndTime(LocalDateTime.now());
        status.setTotalCount(total);
        status.setSuccessCount(success);
        status.setFailedCount(failed);
        status.setStatus(failed > 0 ? "FAILED" : "SUCCESS");
        status.setMessage(message);
        repository.save(status);
    }

    public void finishError(ScrapingStatusRow status, String errorMessage) {
        status.setEndTime(LocalDateTime.now());
        status.setStatus("FAILED");
        status.setMessage(errorMessage);
        repository.save(status);
    }

    public ScrapingStatusRow getCurrentStatus(String page) {
        return repository.findTop1ByPageOrderByStartTimeDesc(page);
    }

    public List<ScrapingStatusRow> getHistory(String page) {
        return repository.findByPageOrderByStartTimeDesc(page);
    }
}

