package com.example.finder.controller;

import com.example.finder.service.scraping.ScrapingService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SearchController {

    private final ScrapingService scraperService;

    public SearchController(ScrapingService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/api/search")
    public String scrapeSearch(@RequestParam String q) {
        return "ok";
    }

    @GetMapping("/test")
    public String test() throws Exception {
        scraperService.scrapeTopVideos();
        return "ok";
    }
}
