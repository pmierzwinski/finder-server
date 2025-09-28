package com.pmierzwinski.finder.modules.scraping.component;

import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import com.pmierzwinski.finder.modules.scraping.repository.ScrapingStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ScrapingStatusComponent {

    private static final Logger log = LoggerFactory.getLogger(ScrapingStatusComponent.class);

    private final ScrapingStatusRepository repository;

    private final ConcurrentHashMap<String, ScrapingStatusRow> running = new ConcurrentHashMap<>();

    public ScrapingStatusComponent(ScrapingStatusRepository repository) {
        this.repository = repository;
    }

    public void onScrapingStarted(String page) {
        ScrapingStatusRow status = new ScrapingStatusRow(page);
        repository.save(status);

        running.put(page, status);
        log.info("Scraping started for page {}", page);
    }

    public void finishSuccess(String page, int total) {
        updateStatus(page, status -> {
            if (total == 0) {
                status.empty();
            } else {
                status.success(total);
            }
            log.info("Scraping success for page {} with {} videos", page, total);
        });
    }

    public void finishError(String page, String errorMessage) {
        updateStatus(page, status -> {
            status.fail(errorMessage);
            log.warn("Scraping failed for page {}: {}", page, errorMessage);
        });
    }

    public List<ScrapingStatusRow> getLastSiteStatuses() {
        return repository.findLatestStatusesPerPage();
    }

    public List<ScrapingStatusRow> getLastSiteStatuses(String page) {
        return repository.findTop10ByPageOrderByStartTimeDesc(page);
    }

    private void updateStatus(String page, java.util.function.Consumer<ScrapingStatusRow> updater) {
        ScrapingStatusRow status = running.get(page);
        if (status == null) {
            log.error("No running status found for page {}. Did you forget to call onScrapingStarted?", page);
            return;
        }

        updater.accept(status);
        repository.save(status);
    }
}
