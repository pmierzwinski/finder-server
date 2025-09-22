package com.example.finder.service.scraping;

import com.example.finder.config.Config;
import com.example.finder.factory.VideoFactory;
import com.example.finder.model.VideoEntity;
import com.example.finder.repository.VideosRepository;
import lombok.Getter;
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
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Getter
public class ScrapingService {

    int ACTION_RECOVERY = 500;

    private static final Logger log = LoggerFactory.getLogger(ScrapingService.class);
    private final VideosRepository repository;

    @Setter
    private Config Xconfig;
    private Config.Site config;

    private WebDriver driver;

    public ScrapingService(Config Xconfig, VideosRepository repository) {
        this.repository = repository;
        this.Xconfig = Xconfig;
    }

    public void scrapeTopVideos() throws InterruptedException {
        initDriver();
        try {
            for (Config.Site xxxConfig : Xconfig.getSites()) {
                config = xxxConfig;
                connect();
                verify();
                initMoreData();
                saveVideos();
            }
        } finally {
            driver.quit();
        }
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

    private void saveVideos() {
        String html = driver.getPageSource();

        saveHtmlToFile(html, "page_dump.html");

        List<VideoEntity> videos = extractVideos(html);

        for (VideoEntity video : videos) {
            log.info(video.toString());
        }

        repository.saveAll(videos);
    }

    private void saveHtmlToFile(String html, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(html);
            log.info("HTML zapisany do pliku: " + fileName);
        } catch (IOException e) {
            log.error("Błąd przy zapisie HTML", e);
        }
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


    private List<VideoEntity> extractVideos(String html) {
        log.info("extracting videos from html");
        List<VideoEntity> videos = new java.util.ArrayList<>(List.of());
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select(config.getGroupSelector().getCss());
        for (Element element : elements) {
            VideoEntity video = VideoFactory.fromJsoupElement(element, config);
            videos.add(video);
        }
        return videos.stream().filter(Objects::nonNull).toList();
    }
}
