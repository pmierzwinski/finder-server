package com.example.finder.scraper;

import com.example.finder.config.SiteConfig;
import com.example.finder.model.VerificationType;
import com.example.finder.service.ScraperService;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
public abstract class BaseScraper {

    private static final Logger log = LoggerFactory.getLogger(ScraperService.class);

    @Setter
    private SiteConfig.Site config;

    private WebDriver driver;

    public BaseScraper() {
        initDriver();
    }

    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/117.0.0.0 Safari/537.36");

        driver = new ChromeDriver(options);
    }


    public void fullFlow() {
        connect();
        if(shouldVerify()) {

        }

    }

    public abstract Boolean shouldVerify();

    public void connect() {
        driver.get("url");
    }

    public void tryToVerify() throws InterruptedException {
        try {//By.id(config.getVerificationSelector())
            List<WebElement> buttons = driver.findElements(By.);
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

    public String scrapeWithSelenium(String site, String url, String selector, int limit, boolean clickConsent) {
        try {
            driver.get("https://www.youtube.com/gaming");
            Thread.sleep(2000);

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
}
