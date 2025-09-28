package com.pmierzwinski.finder.modules.scraping.parsers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import com.pmierzwinski.finder.modules.videos.factory.VideoFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;

public class VideoParser {
    public static List<VideoRow> extractVideos(String html, Config.Page config) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(config.getGroupSelector().getCss());

        return elements.stream()
                .map(e -> VideoFactory.fromJsoupElement(e, config))
                .filter(Objects::nonNull)
                .toList();
    }
}

