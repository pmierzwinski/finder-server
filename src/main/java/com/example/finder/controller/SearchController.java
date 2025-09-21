package com.example.finder.controller;

import com.example.finder.service.ScraperService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SearchController {

    private final ScraperService scraperService;

    public SearchController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/api/search")
    public String scrapeSearch(@RequestParam String q) throws Exception {
        return "ok";
    }

    @GetMapping("/test")
    public String test(@RequestParam String q) throws Exception {




        scraperService.updateTopVideos();
        return "ok";
    }
}
