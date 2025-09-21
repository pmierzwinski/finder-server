package com.example.finder.service;

import com.example.finder.annotation.Scraper;
import com.example.finder.config.SiteConfig;
import com.example.finder.model.ScrapedItem;
import com.example.finder.repository.ScrapedItemRepository;
import com.example.finder.scraper.ScraperStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.NoSuchElementException;

@Service
public class ScraperService {
    private static final Logger log = LoggerFactory.getLogger(ScraperService.class);

    private final SiteConfig config;

    private final ScrapedItemRepository repository;

    private final Map<String, ScraperStrategy> strategies = new HashMap<>();

    public ScraperService(SiteConfig config, ScrapedItemRepository repository, List<ScraperStrategy> strategyList) {
        this.config = config;
        this.repository = repository;

        for (ScraperStrategy strategy : strategyList) {
            Scraper annotation = strategy.getClass().getAnnotation(Scraper.class);
            if (annotation != null) {
                strategies.put(annotation.value(), strategy);
            }
        }
    }


    public void updateTopVideos() {


        config.getSites().forEach(siteConfig -> {
            try {
                var scraper = strategies.get(siteConfig.getId());
                scraper.setConfig(siteConfig);
                scraper.scrape();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        scrapeWithSelenium("YouTube", "https://www.youtube.com/gaming", ".ytd-grid-video-renderer #thumbnail", 10, true);



    }

    public List<ScrapedItem> scrape(String site, String url, String selector, int limit) throws Exception {
        // Sprawdź cache
//        List<ScrapedItem> cached = repository.findBySiteAndDate(site, LocalDate.now());
//        if (!cached.isEmpty()) return cached;


        // Pobierz stronę
        Document doc = Jsoup.connect(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate")
                .followRedirects(true)     // obsługa redirectów
                .get();

        Elements elements = doc.select("a");

        log.info("doc={}", doc);
        log.info("elements={}", elements);


        List<ScrapedItem> results = new ArrayList<>();
        elements.stream().limit(limit).forEach(e -> {
            ScrapedItem item = new ScrapedItem();
            item.setSite(site);
            item.setTitle(e.text());
            item.setUrl("https://www.youtube.com" + e.attr("href"));
            item.setDate(LocalDate.now());
            results.add(item);
        });

        repository.saveAll(results);
        return results;
    }


    public String scrapeWithSelenium(String site, String url, String selector, int limit, boolean clickConsent) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/117.0.0.0 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.youtube.com/gaming");
            Thread.sleep(2000);
            System.out.println(driver.getPageSource().contains("ytd-consent-bump-v2-lightbox"));
            if (driver.getCurrentUrl().contains("consent")) {
                try {
                    List<WebElement> buttons = driver.findElements(By.tagName("span"));
                    for (WebElement btn : buttons) {
                        if (btn.getText().contains("Zaakceptuj wszystko")) {
                            btn.click();
                            System.out.println("Clicked accept");
                            break;
                        }
                    }
                    Thread.sleep(2000); // mała pauza
                } catch (NoSuchElementException ignored) {}
            }
            System.out.println("Current URL: " + driver.getCurrentUrl());
            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            Elements elements = doc.select(".ytd-grid-video-renderer #thumbnail");
            log.info("elements={}", elements);

            return "ok";

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return "error";
        } finally {
            driver.quit();
        }
    }

    private static void manageClicking(boolean clickConsent, WebDriver driver) throws InterruptedException {
        if (clickConsent) {
            try {
                WebElement acceptBtn = driver.findElement(By.cssSelector("button[aria-label='Accept all']"));
                acceptBtn.click();
                Thread.sleep(1000); // mała pauza na odświeżenie strony
            } catch (NoSuchElementException ignored) {
                // brak przycisku - nic nie robimy
            }
        }
    }

}
