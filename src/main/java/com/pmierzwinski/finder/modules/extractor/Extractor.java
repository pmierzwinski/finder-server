package com.pmierzwinski.finder.modules.extractor;

import com.pmierzwinski.finder.modules.extractor.base.BaseGroupDefinition;
import com.pmierzwinski.finder.modules.extractor.base.BaseObjectDefinition;
import com.pmierzwinski.finder.utils.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Extractor {

    public static <T> List<T> extract(String html, Class<T> clazz, BaseGroupDefinition definition) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(definition.getGroupSelector().getCss());

        List<T> results = new ArrayList<>();
        elements.forEach(element -> {
            Map<String, String> extractedData = extractElement(element, definition.getObjectDefinition().getSelectors());
            T object = ObjectMapper.mapToObject(extractedData, clazz);
            results.add(object);
        });

        return results;
    }

    private static Map<String, String> extractElement(Element element, Map<String, SelectorDefinition> selectors) {
        Map<String, String> result = new HashMap<>();
        selectors.forEach((name, selector) -> {
            String value = selector.extractValue(element);
            result.put(name, value);
        });
        return result;
    }
}
