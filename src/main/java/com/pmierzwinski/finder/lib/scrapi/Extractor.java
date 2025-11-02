package com.pmierzwinski.finder.lib.scrapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmierzwinski.finder.config.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Extractor {


    public static <T> T scrapePage(ScrapiAppConfig pageConfig, Class<T> clazz) {
        WebManager webManager = new WebManager();
        String pageHtml = webManager.getSiteHtml(pageConfig.dataUrl, pageConfig.verificationActions);
        return Extractor.tryParse(pageHtml, pageConfig.pageSelectors, clazz);
    }


    public static <T> T tryParse(String html, Map<String, SelectorDefinition> config, Class<T> targetType) {
        try {
            return Extractor.parse(html, config, targetType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * Typowany parser HTML → obiekt domenowy.
     */
    public static <T> T parse(String html, Map<String, SelectorDefinition> config, Class<T> targetType) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Map<String, String>>> mapResult = parseToMap(html, config);
        String json = mapper.writeValueAsString(mapResult);
        return mapper.readValue(json, targetType);
    }

    /**
     * Dynamiczny parser HTML → mapowana struktura JSON.
     */
    public static Map<String, List<Map<String, String>>> parse(String html, Map<String, SelectorDefinition> config) {
        return parseToMap(html, config);
    }

    /**
     * Główna logika przetwarzania HTML.
     */
    private static Map<String, List<Map<String, String>>> parseToMap(String html, Map<String, SelectorDefinition> config) {
        Document doc = Jsoup.parse(html);
        Map<String, List<Map<String, String>>> result = new LinkedHashMap<>();

        for (Map.Entry<String, SelectorDefinition> entry : config.entrySet()) {
            String key = entry.getKey();
            SelectorDefinition def = entry.getValue();

            Elements elements = doc.select(def.getListSelector());
            List<Map<String, String>> list = new ArrayList<>();

            for (Element el : elements) {
                Map<String, String> obj = new LinkedHashMap<>();
                for (var field : def.getFieldSelectors().entrySet()) {
                    obj.put(field.getKey(), el.select(field.getValue()).text());
                }
                list.add(obj);
            }

            result.put(key, list);
        }

        return result;
    }

}
