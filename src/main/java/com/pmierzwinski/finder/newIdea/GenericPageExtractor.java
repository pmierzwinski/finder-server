package com.pmierzwinski.finder.newIdea;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmierzwinski.finder.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GenericPageExtractor {

    private final ObjectMapper mapper;
    private final EntityRegistry registry;
    private final GenericExtractor extractor;

    public Page parse(Config.PageConfig pageConfig, String html) {
        Map<String, List<Map<String, String>>> parsed = extractor.extractAll(pageConfig, html);
        Map<String, List<? extends PageData>> entities = new LinkedHashMap<>();

        parsed.forEach((key, maps) -> {
            Class<?> type = registry.getEntityType(key);
            if (type == null) return;
            List<PageData> list = maps.stream()
                    .map(map -> mapper.convertValue(map, type))
                    .map(PageData.class::cast)
                    .peek(e -> e.enrich(pageConfig))
                    .toList();
            entities.put(key, list);
        });

        return buildPage(pageConfig, entities);
    }

    private Page buildPage(Config.PageConfig config, Map<String, List<? extends PageData>> entities) {
        try {
            var ctor = Page.class.getConstructors()[0];
            var args = new ArrayList<>();
            args.add(config.getName());
            for (Parameter p : ctor.getParameters()) {
                if (p.getType().equals(String.class)) continue;
                args.add(entities.getOrDefault(p.getName(), List.of()));
            }
            return (Page) ctor.newInstance(args.toArray());
        } catch (Exception e) {
            throw new RuntimeException("Error creating Page", e);
        }
    }
}
