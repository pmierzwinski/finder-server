package com.pmierzwinski.finder.modules.scraping.component;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class WebManager implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(WebManager.class);

    private static final int ACTION_RECOVERY = 500;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    private WebDriver driver;

    public WebManager() {
        initDriver();
    }

    public String getSiteHtml(String pageUrl) {
        try {
            connect(pageUrl);
            acceptCookiesIfPresent();
            initMoreData();
            return driver.getPageSource();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while fetching site: " + pageUrl, e);
        }
    }

    private void initDriver() {
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
        log.info("ChromeDriver started");
    }

    private void connect(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(ACTION_RECOVERY);
        log.debug("Connected to {}", url);
    }

    private void acceptCookiesIfPresent() throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            List<WebElement> buttons = driver.findElements(By.cssSelector("span"));

            for (WebElement btn : buttons) {
                if (btn.getText().contains("Zaakceptuj wszystko")) {
                    wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                    Thread.sleep(ACTION_RECOVERY);
                    log.info("Accepted cookies banner");
                    return;
                }
            }
        } catch (Exception e) {
            log.debug("No cookies banner found");
        }
    }

    private void initMoreData() throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom='0.5'");
            Thread.sleep(ACTION_RECOVERY);
            log.debug("Adjusted page zoom for more data");
        } catch (Exception e) {
            log.warn("initMoreData failed: {}", e.getMessage());
        }
    }

    @Override
    public void close() {
        if (driver != null) {
            driver.quit();
            driver = null;
            log.info("ChromeDriver closed");
        }
    }
}
