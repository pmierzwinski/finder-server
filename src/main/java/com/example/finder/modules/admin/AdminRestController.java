package com.example.finder.modules.admin;

import com.example.finder.modules.scraping.db.ScrapingStatusRow;
import com.example.finder.modules.scraping.component.ScrapingStatusComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final ScrapingStatusComponent scrapingStatusService;

    public AdminRestController(ScrapingStatusComponent scrapingStatusService) {
        this.scrapingStatusService = scrapingStatusService;
    }

    @GetMapping("/status/{page}/current")
    public ScrapingStatusRow getCurrentStatus(@PathVariable String page) {
        return scrapingStatusService.getCurrentStatus(page);
    }

    @GetMapping("/status/{page}/history")
    public List<ScrapingStatusRow> getHistory(@PathVariable String page) {
        return scrapingStatusService.getHistory(page);
    }
}

