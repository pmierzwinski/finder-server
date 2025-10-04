package com.pmierzwinski.finder.modules.scraping.component;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlExtractor {
    private final List<FieldDefinition> definitions;

    public HtmlExtractor(List<FieldDefinition> definitions) {
        this.definitions = definitions;
    }

    public Map<String, String> extract(String html) {
        Map<String, String> result = new HashMap<>();
        Document doc = Jsoup.parse(html);

        for (FieldDefinition def : definitions) {
            Element element = doc.selectFirst(def.selector());
            if (element != null) {
                result.put(def.name(), element.text());
            }
        }

        return result;
    }
}

