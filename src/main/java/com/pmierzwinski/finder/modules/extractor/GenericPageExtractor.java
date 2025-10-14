package com.pmierzwinski.finder.modules.extractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmierzwinski.finder.config.Config;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

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

            Config.GroupDefinition<?> groupConfig = (Config.GroupDefinition<?>) getConfigField(config, field.getName());
            if (groupConfig == null) continue;

            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Class<?> itemType = (Class<?>) type.getActualTypeArguments()[0];

            List<Map<String, String>> extracted = extractor.extractToMaps(doc, groupConfig);

            List<?> items = extracted.stream()
                    .map(map -> mapper.convertValue(map, itemType))
                    .toList();

            try {
                Config.GroupDefinition<?> group = (Config.GroupDefinition<?>) field.get(page);
                group.setGroupCss(groupConfig.getGroupCss());
                group.setFields(groupConfig.getFields());
                group.setItems((List) items);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
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
