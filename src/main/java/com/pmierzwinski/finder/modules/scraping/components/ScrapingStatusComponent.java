package com.pmierzwinski.finder.modules.scraping.components;

import com.pmierzwinski.finder.modules.scraping.repository.ScrapingStatusEntity;
import com.pmierzwinski.finder.modules.scraping.repository.ScrapingStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScrapingStatusComponent {

    private static final Logger log = LoggerFactory.getLogger(ScrapingStatusComponent.class);

    private final ScrapingStatusRepository repository;

    private final ConcurrentHashMap<String, ScrapingStatusEntity> running = new ConcurrentHashMap<>();

    public ScrapingStatusComponent(ScrapingStatusRepository repository) {
        this.repository = repository;
    }

    public void onScrapingStarted(String pageId) {
        ScrapingStatusEntity status = new ScrapingStatusEntity(pageId);
        repository.save(status);

        running.put(pageId, status);
        log.info("Scraping started for page {}", pageId);
    }

    public void finishSuccess(String pageId, int total) {
        updateStatus(pageId, status -> {
            if (total == 0) {
                status.empty();
            } else {
                status.success(total);
            }
            log.info("Scraping success for page {} with {} videos", pageId, total);
        });
    }

    public void finishError(String pageId, String errorMessage) {
        updateStatus(pageId, status -> {
            status.fail(errorMessage);
            log.warn("Scraping failed for page {}: {}", pageId, errorMessage);
        });
    }

    public List<ScrapingStatusEntity> getLastSiteStatuses() {
        return repository.findLatestStatusesPerPage();
    }

    public List<ScrapingStatusEntity> getLastSiteStatuses(String page) {
        return repository.findTop10ByPageOrderByStartTimeDesc(page);
    }

    private void updateStatus(String page, java.util.function.Consumer<ScrapingStatusEntity> updater) {
        ScrapingStatusEntity status = running.get(page);
        if (status == null) {
            log.error("No running status found for page {}. Did you forget to call onScrapingStarted?", page);
            return;
        }

        updater.accept(status);
        repository.save(status);
    }
}
