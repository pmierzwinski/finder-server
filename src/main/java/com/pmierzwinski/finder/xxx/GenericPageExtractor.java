package com.pmierzwinski.finder.xxx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmierzwinski.finder.config.Config;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenericPageExtractor {

    private final ObjectMapper mapper;
    private final GenericExtractor extractor;

    public Page parse(Config.PageConfig config, String html) throws IllegalAccessException {
        Page page = new Page();
        Document doc = Jsoup.parse(html);

        for (Field field : Page.class.getDeclaredFields()) {
            field.setAccessible(true);

            // znajdź GroupDefinition<?> z configu (np. videos)
            Config.GroupDefinition<?> groupConfig = (Config.GroupDefinition<?>) getConfigField(config, field.getName());
            if (groupConfig == null) continue;

            // wykryj typ generyczny pola (np. VideoRow)
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Class<?> itemType = (Class<?>) type.getActualTypeArguments()[0];

            // wyciągnij dane HTML
            List<Map<String, String>> extracted = extractor.extractToMaps(doc, groupConfig);

            // zamapuj dane na typ (np. VideoRow)
            List<?> items = extracted.stream()
                    .map(map -> mapper.convertValue(map, itemType))
                    .toList();

            // ustaw dane w Page
            Config.GroupDefinition<?> group = (Config.GroupDefinition<?>) field.get(page);
            group.setGroupCss(groupConfig.getGroupCss());
            group.setFields(groupConfig.getFields());
            group.setItems((List) items);
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
