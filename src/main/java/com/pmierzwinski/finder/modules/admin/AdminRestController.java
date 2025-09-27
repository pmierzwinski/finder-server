package com.pmierzwinski.finder.modules.admin;

import com.pmierzwinski.finder.modules.scraping.ScrapingService;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final ScrapingService scrapingStatusService;

    public AdminRestController(ScrapingService scrapingStatusService) {
        this.scrapingStatusService = scrapingStatusService;
    }

    @GetMapping("/status")
    public List<ScrapingStatusRow> getLastScrapingStatuses() {
        return scrapingStatusService.getLastScrapingStatuses();
    }

    @GetMapping("/status/{site}")
    public List<ScrapingStatusRow> getLastScrapingStatuses(@PathVariable String site) {
        return scrapingStatusService.getLastScrapingStatuses(site);
    }

    //todo /datapile
}

