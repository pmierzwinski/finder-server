package com.pmierzwinski.finder.modules.scraping.parsers;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.candidate.VideoCandidate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;

public class VideoParser {

    public static List<VideoCandidate> extractVideos(String html, Config.Page config) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(config.getGroupSelector().getCss());

        return elements.stream()
                .map(e -> VideoParser.fromJsoupElement(e, config))
                .filter(Objects::nonNull)
                .toList();
    }

    private static VideoCandidate fromJsoupElement(Element element, Config.Page config) {
        VideoCandidate candidate = getCandidate(element, config);
        if(!candidate.isValid()) {
            return null;
        }

        return candidate;
    }

    private static VideoCandidate getCandidate(Element element, Config.Page config) {
        return new VideoCandidate(
                config.getId(),
                getForSelector(element, config.getContentUrlSelector()),
                getForSelector(element, config.getTitleSelector()),
                getForSelector(element, config.getDescriptionSelector()),
                getForSelector(element, config.getImageSelector())
        );
    }

    private static String getForSelector(Element element, Config.Selector selector) {
        return selector.getTag() == null
                ? element.select(selector.getCss()).text()
                : element.select(selector.getCss()).attr(selector.getTag());
    }
}
