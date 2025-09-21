package com.example.finder.scraper;

import com.example.finder.model.ScrapedItem;
import com.example.finder.repository.ScrapedItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestScraper implements ScraperStrategy {

    private final ScrapedItemRepository repository;

    public TestScraper(ScrapedItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getSiteName() {
        return "youtube";
    }

    @Override
    public List<ScrapedItem> scrape(int limit, boolean clickConsent) throws Exception {
        List<ScrapedItem> results = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/117.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.youtube.com/feed/trending");
            Thread.sleep(2000);

            if (clickConsent && driver.getCurrentUrl().contains("consent")) {
                try {
                    WebElement acceptBtn = driver.findElement(By.xpath("//span[contains(text(),'Zaakceptuj wszystko')]"));
                    acceptBtn.click();
                    Thread.sleep(2000);
                } catch (NoSuchElementException ignored) {}
            }

            String html = driver.getPageSource();
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("ytd-video-renderer #video-title");

            elements.stream().limit(limit).forEach(e -> {
                ScrapedItem item = new ScrapedItem();
                item.setSite(getSiteName());
                item.setTitle(e.text());
                String href = e.attr("href");
                if (!href.startsWith("http")) {
                    href = "https://www.youtube.com" + href;
                }
                item.setUrl(href);
                item.setDate(LocalDate.now());
                results.add(item);
            });

            repository.saveAll(results);
        } finally {
            driver.quit();
        }

        return results;
    }
}
