package com.pmierzwinski.finder.modules.extractor;

import com.pmierzwinski.finder.config.Config;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GenericExtractor {

    public List<Map<String, String>> extractToMaps(Document doc, Config.GroupDefinition<?> def) {
        List<Map<String, String>> results = new ArrayList<>();

        for (Element group : doc.select(def.getGroupCss())) {
            Map<String, String> item = new LinkedHashMap<>();

            def.getFields().forEach((name, sel) -> {
                Elements els = group.select(sel.getCss());
                String value = switch (sel.getTag()) {
                    case "text" -> els.text();
                    case "src", "href" -> els.attr(sel.getTag());
                    default -> els.attr(sel.getTag());
                };
                item.put(name, value);
            });

            results.add(item);
        }

        return results;
    }
}