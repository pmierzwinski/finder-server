package com.pmierzwinski.finder.modules.extractor.components;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Extractor<T> {

    private final Class<T> clazz;
    private final String groupSelector;
    private final List<FieldDefinition> fields;

    public Extractor(Class<T> clazz, String groupSelector, List<FieldDefinition> fields) {
        this.clazz = clazz;
        this.groupSelector = groupSelector;
        this.fields = fields;
    }

    public List<T> extract(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(groupSelector);
        HtmlExtractor htmlExtractor = new HtmlExtractor(fields);

        List<T> results = new ArrayList<>();
        elements.forEach(element -> {
            Map<String, String> extractedData = htmlExtractor.extract(element);
            T object = ObjectMapper.mapToObject(extractedData, clazz);

            results.add(object);
        });

        return results;
    }

}
