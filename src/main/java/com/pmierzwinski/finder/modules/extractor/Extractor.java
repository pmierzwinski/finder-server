package com.pmierzwinski.finder.modules.extractor;

import com.pmierzwinski.finder.modules.extractor.components.ExtractDefinition;
import com.pmierzwinski.finder.modules.extractor.components.HtmlExtractor;
import com.pmierzwinski.finder.modules.extractor.components.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Extractor<T> {

    private final Class<T> clazz;
    private final ExtractDefinition definition;

    public Extractor(Class<T> clazz, ExtractDefinition definition) {
        this.clazz = clazz;
        this.definition = definition;
    }

    public List<T> extract(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(definition.getGroupSelector());

        List<T> results = new ArrayList<>();
        elements.forEach(element -> {
            T object = mapHtmlToObject(element);
            results.add(object);
        });

        return results;
    }

    public T mapHtmlToObject(Element element) {
        Map<String, String> extractedData = new HtmlExtractor(definition.getDefinitions()).extract(element);
        return ObjectMapper.mapToObject(extractedData, clazz);
    }


}
