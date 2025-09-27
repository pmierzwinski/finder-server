package com.pmierzwinski.finder.modules.scraping.helpers;

import com.pmierzwinski.finder.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.NoSuchElementException;

public class WebManager {

    int ACTION_RECOVERY = 500;

    private WebDriver driver;

    public WebManager() {
        initDriver();
    }

    public String getSiteHtml(Config.Site siteConfig) throws InterruptedException {
        connect(siteConfig.getDataUrl());
        verify();
        initMoreData();

        return driver.getPageSource();
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

    private void connect(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(ACTION_RECOVERY);
    }

    private void verify() throws InterruptedException {
        try {
            WebElement btn = findAcceptButton();
            if(btn == null) {
                return;
            }
            btn.click();
            Thread.sleep(ACTION_RECOVERY);
        } catch (NoSuchElementException ignored) {}
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

    private void initMoreData() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='" + 0.5 + "'");
        Thread.sleep(ACTION_RECOVERY);
    }

    public void quit() {
        if(driver != null) {
            driver.quit();
        }
    }
}
