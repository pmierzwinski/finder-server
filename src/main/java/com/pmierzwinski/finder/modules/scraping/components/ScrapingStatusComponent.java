package com.pmierzwinski.finder.modules.scraping.components;

import com.pmierzwinski.finder.modules.scraping.model.ScrapingStatusEntity;
import com.pmierzwinski.finder.modules.scraping.repository.ScrapingStatusRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScrapingStatusComponent {

    private final ScrapingStatusRepository repository;

    public ScrapingStatusComponent(ScrapingStatusRepository repository) {
        this.repository = repository;
    }

    public void startScraping(String pageId) {
        var last = repository.findTopByPageIdOrderByStartTimeDesc(pageId);
        if (last != null && last.getStatus() == ScrapingStatusEntity.Status.RUNNING) {
            throw new IllegalStateException("Scraping already running for " + pageId);
        }

        var status = new ScrapingStatusEntity();
        status.setPageId(pageId);
        status.markRunning();
        repository.save(status);
    }

    public void finishScraping(String pageId, int total) {
        var status = getRunning(pageId);
        status.markSuccess(total);
        repository.save(status);
    }

    public void failScraping(String pageId, String msg) {
        var status = getRunning(pageId);
        status.markFailed(msg);
        repository.save(status);
    }

    private ScrapingStatusEntity getRunning(String pageId) {
        var status = repository.findTopByPageIdOrderByStartTimeDesc(pageId);
        if (status == null || status.getStatus() != ScrapingStatusEntity.Status.RUNNING) {
            throw new IllegalStateException("No running scraping found for " + pageId);
        }

        return status;
    }

    public List<ScrapingStatusEntity> getLastStatuses() {
        return repository.findLatestStatusesPerPage();
    }

    public List<ScrapingStatusEntity> getLastStatuses(String pageId) {
        return repository.findTop10ByPageIdOrderByStartTimeDesc(pageId);
    }
}

