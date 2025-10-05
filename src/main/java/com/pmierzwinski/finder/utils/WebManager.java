package com.pmierzwinski.finder.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class WebManager {

    private static final Logger log = LoggerFactory.getLogger(WebManager.class);
    private static final int ACTION_RECOVERY = 500;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    public String getSiteHtml(String pageUrl, String verifierCss) {
        ChromeDriver driver = null;
        try {
            driver = initDriver();
            driver.get(pageUrl);
            Thread.sleep(ACTION_RECOVERY);

            acceptCookiesIfPresent(driver);
            adjustZoom(driver);

            if (verifierCss != null && !verifierCss.isBlank()) {
                verifyPage(driver, verifierCss);
            }

            return driver.getPageSource();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while fetching: " + pageUrl, e);
        } catch (Exception e) {
            throw new RuntimeException("Scraping failed for " + pageUrl + ": " + e.getMessage(), e);
        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception ignored) {}
            }
        }
    }

    private ChromeDriver initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/117.0.0.0 Safari/537.36");
        options.addArguments("window-size=1920,1080");
        return new ChromeDriver(options);
    }

    private void acceptCookiesIfPresent(WebDriver driver) throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            List<WebElement> buttons = driver.findElements(By.cssSelector("span"));
            for (WebElement btn : buttons) {
                if (btn.getText().contains("Zaakceptuj wszystko")) {
                    wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                    Thread.sleep(ACTION_RECOVERY);
                    return;
                }
            }
        } catch (Exception ignored) {}
    }

    private void adjustZoom(WebDriver driver) throws InterruptedException {
        try {
            ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='0.5'");
            Thread.sleep(ACTION_RECOVERY);
        } catch (Exception ignored) {}
    }

    private void verifyPage(WebDriver driver, String cssSelector) {
        try {
            driver.findElement(By.cssSelector(cssSelector));
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("Verification failed: element not found (" + cssSelector + ")");
        }
    }
}
