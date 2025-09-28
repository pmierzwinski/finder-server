package com.pmierzwinski.finder.modules.scraping.component;

import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import com.pmierzwinski.finder.modules.scraping.repository.ScrapingStatusRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ScrapingStatusComponent {

    private final ScrapingStatusRepository repository;

    private final HashMap<String, ScrapingStatusRow> running = new HashMap<>();

    public ScrapingStatusComponent(ScrapingStatusRepository repository) {
        this.repository = repository;
    }

    public void onScrapingStarted(String page) {
        ScrapingStatusRow status = new ScrapingStatusRow(page);
        repository.save(status);

        running.put(page, status);
    }

    public void finishSuccess(String page, int total) {
        ScrapingStatusRow status = running.get(page);

        if(total == 0) {
            status.empty();
        } else {
            status.success(total);
        }

        repository.save(status);
    }

    public void finishError(String page, String errorMessage) {
        ScrapingStatusRow status = running.get(page);
        status.fail(errorMessage);

        repository.save(status);
    }

    public List<ScrapingStatusRow> getLastSiteStatuses() {
        return repository.findLatestStatusesPerPage();
    }

    public List<ScrapingStatusRow> getLastSiteStatuses(String page) {
        return repository.findTop10ByPageOrderByStartTimeDesc(page);
    }

}

