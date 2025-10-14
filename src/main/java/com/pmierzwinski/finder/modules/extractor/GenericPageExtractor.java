package com.pmierzwinski.finder.modules.extractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmierzwinski.finder.config.Config;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenericPageExtractor {

    private final ObjectMapper mapper;
    private final GenericExtractor extractor;

    public Page parse(Config.PageConfig config, String html) {
        Page page = new Page();
        Document doc = Jsoup.parse(html);

        for (Field field : Page.class.getDeclaredFields()) {
            field.setAccessible(true);

            // znajdź odpowiedni obiekt GroupDefinition z PageConfig
            Config.GroupDefinition<?> configGroup = (Config.GroupDefinition<?>) getConfigField(config, field.getName());
            if (configGroup == null) continue;

            // pobierz typ generyczny dla tej grupy
            Config.GroupDefinition<?> pageGroup;
            try {
                pageGroup = (Config.GroupDefinition<?>) field.get(page);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            // parsowanie HTML → mapy
            List<Map<String, String>> extracted = extractor.extractToMaps(doc, configGroup.asFieldMap(), configGroup.getGroupCss());

            // konwersja map → typy docelowe (np. VideoRow, HeaderRow)
            List<?> items = extracted.stream()
                    .map(map -> mapper.convertValue(map, configGroup.getTargetType()))
                    .toList();

            // przypisanie wyników do obiektu Page
            pageGroup.setGroupCss(configGroup.getGroupCss());
            pageGroup.setItems((List) items);
        }

        return page;
    }

    private Object getConfigField(Config.PageConfig config, String fieldName) {
        try {
            Field f = config.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(config);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
