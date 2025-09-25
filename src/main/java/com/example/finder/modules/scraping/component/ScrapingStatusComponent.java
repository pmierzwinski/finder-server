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

    public ScrapingStatusRow initRunningState(String page) {

        ScrapingStatusRow status = new ScrapingStatusRow();
        status.setPage(page);
        status.setStartTime(LocalDateTime.now());
        status.setStatus("RUNNING");
        status.setMessage("Scraping top videos...");

        return repository.save(status);
    }

    public void finishSuccess(ScrapingStatusRow status, int total) {
        status.setEndTime(LocalDateTime.now());
        status.setTotalCount(total);
        status.setStatus("SUCCESS");
        status.setMessage("Update completed");
        repository.save(status);
    }

    public void finishError(ScrapingStatusRow status, String errorMessage) {
        if(status == null) {
            status = new ScrapingStatusRow();
        }
        status.setEndTime(LocalDateTime.now());
        status.setStatus("FAILED");
        status.setMessage(errorMessage);
        repository.save(status);
    }

    public List<ScrapingStatusRow> getPagesStatuses() {
        return repository.findLatestStatusesPerPage();
    }

    public List<ScrapingStatusRow> getTopPageStatuses(String page) {
        return repository.findTop10ByPageOrderByStartTimeDesc(page);
    }

}

