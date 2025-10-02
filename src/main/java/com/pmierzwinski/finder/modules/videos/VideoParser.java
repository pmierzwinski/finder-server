package com.pmierzwinski.finder.modules.videos;

import com.pmierzwinski.finder.config.Config;
import com.pmierzwinski.finder.modules.scraping.video.VideoCandidate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;

public class VideoParser {

    public static List<VideoCandidate> extractVideos(String html, Config.Page config) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(config.getVideoGroupSelector().getCss());

        return elements.stream()
                .map(e -> VideoParser.fromJsoupElement(e, config))
                .filter(Objects::nonNull)
                .toList();
    }

    private static VideoCandidate fromJsoupElement(Element element, Config.Page config) {
        VideoCandidate candidate = createCandidate(element, config);
        if(!candidate.isValid()) {
            return null;
        }

        return candidate;
    }

    private static VideoCandidate createCandidate(Element element, Config.Page config) {
        return new VideoCandidate(
                config.getId(),
                getForSelector(element, config.getVideoUrlSelector()),
                getForSelector(element, config.getVideoTitleSelector()),
                getForSelector(element, config.getVideoDescriptionSelector()),
                getForSelector(element, config.getVideoImageSelector())
        );
    }

    private static String getForSelector(Element element, Config.GroupSelector selector) {
        return selector.getTag() == null
                ? element.select(selector.getCss()).text()
                : element.select(selector.getCss()).attr(selector.getTag());
    }
}
