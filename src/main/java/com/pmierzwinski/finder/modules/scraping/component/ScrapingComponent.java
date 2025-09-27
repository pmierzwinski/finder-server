package com.pmierzwinski.finder.modules.scraping.component;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.db.ScrapingStatusRow;
import com.pmierzwinski.finder.modules.scraping.helpers.WebManager;
import com.pmierzwinski.finder.modules.videos.factory.VideoFactory;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ScrapingComponent {

    //todo loger to file and show it in admin
    private static final Logger log = LoggerFactory.getLogger(ScrapingComponent.class);

    @Setter
    private Config config;

    private final ScrapingStatusComponent scrapingStatusComponent;

    private final WebManager webManager;

    public ScrapingComponent(ScrapingStatusComponent scrapingStatusComponent, Config config) {
        this.config = config;
        this.scrapingStatusComponent = scrapingStatusComponent;

        this.webManager = new WebManager();
    }

    public List<VideoRow> scrapeTopVideos() throws Exception {
        try {
            List<VideoRow> videos = new ArrayList<>();
            for (Config.Site siteConfig : config.getSites()) {
                videos.addAll(getFromPage(siteConfig));
            }
            return videos;
        } finally {
            webManager.quit();
        }
    }

    private List<VideoRow> getFromPage(Config.Site siteConfig) throws Exception {

        var pageId = siteConfig.getId();

        scrapingStatusComponent.onScrapingStarted(siteConfig.getId());

        try {
            //todo change it to other class - the config - it should be set in constructor
            var videos = getVideos(webManager.getSiteHtml(siteConfig), siteConfig);

            scrapingStatusComponent.finishSuccess(pageId, videos.size());

            return videos;
        }  catch (Exception e) {
            scrapingStatusComponent.finishError(pageId, "Error: " + e.getMessage());
            throw e;
        }
    }

    private List<VideoRow> getVideos(String html, Config.Site siteConfig) {
        List<VideoRow> videos = extractVideos(html, siteConfig);

        for (VideoRow video : videos) {
            log.info(video.toString());
        }

        return videos;
    }

    private List<VideoRow> extractVideos(String html, Config.Site config) {
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
