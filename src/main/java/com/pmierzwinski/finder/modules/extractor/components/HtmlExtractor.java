package com.pmierzwinski.finder.modules.extractor.components;

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

    public Map<String, String> extract(Element element) {
        Map<String, String> result = new HashMap<>();

        for (FieldDefinition def : definitions) {
            if (element != null) {
                result.put(def.name(), element.text());
            }
        }

        return result;
    }
}

