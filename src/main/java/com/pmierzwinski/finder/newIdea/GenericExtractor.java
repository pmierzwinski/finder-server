package com.pmierzwinski.finder.newIdea;

import com.pmierzwinski.finder.config.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GenericExtractor {

    public List<Map<String, String>> extract(Config.LibGroupSelector groupConfig, String html) {
        Document doc = Jsoup.parse(html);
        List<Map<String, String>> results = new ArrayList<>();

        for (Element group : doc.select(groupConfig.getGroupCss())) {
            Map<String, String> item = new LinkedHashMap<>();

            for (var field : groupConfig.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(groupConfig);
                    if (value instanceof Config.LibSelector selector) {
                        String fieldName = field.getName();
                        String extracted = extractValue(group, selector);
                        item.put(fieldName, extracted);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            results.add(item);
        }
        return results;
    }

    public Map<String, List<Map<String, String>>> extractAll(Config.PageConfig page, String html) {
        Map<String, List<Map<String, String>>> result = new LinkedHashMap<>();

        for (var field : page.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(page);
                if (value instanceof Config.LibGroupSelector selector) {
                    result.put(field.getName(), extract(selector, html));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private String extractValue(Element group, Config.LibSelector selector) {
        var selected = group.select(selector.getCss());
        return switch (selector.getTag()) {
            case "text" -> selected.text();
            case "src" -> selected.attr("src");
            case "href" -> selected.attr("href");
            default -> selected.attr(selector.getTag());
        };
    }
}