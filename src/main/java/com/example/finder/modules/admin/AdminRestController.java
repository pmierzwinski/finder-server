package com.example.finder.modules.admin;

import com.example.finder.modules.scraping.ScrapingService;
import com.example.finder.modules.scraping.db.ScrapingStatusRow;
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
    public List<ScrapingStatusRow> getCurrentStatus() {
        return scrapingStatusService.getPagesStatuses();
    }

    @GetMapping("/status/{page}")
    public List<ScrapingStatusRow> getCurrentStatus(@PathVariable String page) {
        return scrapingStatusService.getTopPageStatuses(page);
    }

    //todo /datapile
}

