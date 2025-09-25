package com.example.finder.modules.scraping.component;

import com.example.finder.config.Config;
import com.example.finder.modules.videos.factory.VideoFactory;
import com.example.finder.modules.videos.db.VideoRow;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
public class ScrapingComponent {

    int ACTION_RECOVERY = 500;

    private static final Logger log = LoggerFactory.getLogger(ScrapingComponent.class);

    @Setter
    private Config Xconfig;
    private final ScrapingStatusComponent scrapingStatusComponent;
    private Config.Site config;

    private WebDriver driver;

    public ScrapingComponent(ScrapingStatusComponent scrapingStatusComponent, Config Xconfig) {
        this.Xconfig = Xconfig;
        this.scrapingStatusComponent = scrapingStatusComponent;
    }

    public List<VideoRow> scrapeTopVideos() throws InterruptedException {
        var status = scrapingStatusComponent.start("youtube", "Scraping top videos...");
        initDriver();
        try {
            for (Config.Site xxxConfig : Xconfig.getSites()) {
                config = xxxConfig;
                connect();
                verify();
                initMoreData();
                var videos = getVideos();

                scrapingStatusComponent.finishSuccess(
                        status,
                        videos.size(),
                        videos.size(),
                        0,
                        "Update completed"
                );

                return videos;
            }
        } catch (Exception e) {
            scrapingStatusComponent.finishError(status, "Error: " + e.getMessage());
            throw e;
        } finally {
            driver.quit();
        }
        return null;
    }


    private void initMoreData() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='" + 0.5 + "'");
        Thread.sleep(ACTION_RECOVERY);
    }

    private void connect() throws InterruptedException {
        log.info("connecting to " + config.getDataUrl());
        driver.get(config.getDataUrl());
        Thread.sleep(ACTION_RECOVERY);
    }

    private void verify() throws InterruptedException {
        try {
            log.info("verifying policy");
            WebElement btn = findAcceptButton();
            if(btn == null) {
                log.info("no accept button found");
                return;
            }
            log.info("clicking accept button");
            btn.click();
            Thread.sleep(ACTION_RECOVERY);
        } catch (NoSuchElementException ignored) {}
    }

    private List<VideoRow> getVideos() {
        String html = driver.getPageSource();
        List<VideoRow> videos = extractVideos(html);

        for (VideoRow video : videos) {
            log.info(video.toString());
        }

        return videos;
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
        options.addArguments("window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    private WebElement findAcceptButton() {
        List<WebElement> buttons = driver.findElements(By.cssSelector("span"));
        for (WebElement btn : buttons) {
            if (btn.getText().contains("Zaakceptuj wszystko")) {
                return btn;
            }
        }
        return null;
    }


    private List<VideoRow> extractVideos(String html) {
        log.info("extracting videos from html");
        List<VideoRow> videos = new java.util.ArrayList<>(List.of());
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select(config.getGroupSelector().getCss());
        for (Element element : elements) {
            VideoRow video = VideoFactory.fromJsoupElement(element, config);
            videos.add(video);
        }
        return videos.stream().filter(Objects::nonNull).toList();
    }
}
