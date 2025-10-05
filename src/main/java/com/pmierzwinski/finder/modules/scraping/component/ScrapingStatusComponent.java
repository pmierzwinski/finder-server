package com.pmierzwinski.finder.modules.scraping.component;

import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
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

    private final ConcurrentHashMap<PageId, ScrapingStatusRow> running = new ConcurrentHashMap<>();

    public ScrapingStatusComponent(ScrapingStatusRepository repository) {
        this.repository = repository;
    }

    public void onScrapingStarted(PageId pageId) {
        ScrapingStatusRow status = new ScrapingStatusRow(pageId);
        repository.save(status);

        running.put(pageId, status);
        log.info("Scraping started for page {}", pageId.getValue());
    }

    public void finishSuccess(PageId pageId, int total) {
        updateStatus(pageId, status -> {
            if (total == 0) {
                status.empty();
            } else {
                status.success(total);
            }
            log.info("Scraping success for page {} with {} videos", pageId.getValue(), total);
        });
    }

    public void finishError(PageId pageId, String errorMessage) {
        updateStatus(pageId, status -> {
            status.fail(errorMessage);
            log.warn("Scraping failed for page {}: {}", pageId.getValue(), errorMessage);
        });
    }

    public List<ScrapingStatusRow> getLastSiteStatuses() {
        return repository.findLatestStatusesPerPage();
    }

    public List<ScrapingStatusRow> getLastSiteStatuses(String page) {
        return repository.findTop10ByPageOrderByStartTimeDesc(page);
    }

    private void updateStatus(PageId page, java.util.function.Consumer<ScrapingStatusRow> updater) {
        ScrapingStatusRow status = running.get(page);
        if (status == null) {
            log.error("No running status found for page {}. Did you forget to call onScrapingStarted?", page);
            return;
        }

        updater.accept(status);
        repository.save(status);
    }
}
