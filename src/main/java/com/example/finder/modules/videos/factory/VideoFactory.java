package com.example.finder.modules.videos.factory;

import com.example.finder.config.Config;
import com.example.finder.modules.videos.model.VideoCandidate;
import com.example.finder.modules.videos.db.VideoRow;
import org.jsoup.nodes.Element;

public class VideoFactory {

    public static VideoRow fromJsoupElement(Element element, Config.Site config) {
        VideoCandidate candidate = getCandidate(element, config);
        if(!candidate.isValid()) {
            return null;
        }

        return candidate.toEntity();
    }

    public static VideoCandidate getCandidate(Element element, Config.Site config) {
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
