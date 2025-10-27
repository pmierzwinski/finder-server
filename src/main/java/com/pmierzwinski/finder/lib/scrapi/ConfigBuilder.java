package com.pmierzwinski.finder.lib.scrapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigBuilder {

    private final Map<String, SelectorDefinition> selectors = new LinkedHashMap<>();

    // --- Dodawanie sekcji ręcznie ---
    public ConfigBuilder addSection(String name, String listSelector, Map<String, String> fields) {
        SelectorDefinition def = new SelectorDefinition();
        def.setListSelector(listSelector);
        def.setFieldSelectors(fields);
        selectors.put(name, def);
        return this;
    }

    // --- Wczytanie z YAML (String) ---
    public ConfigBuilder fromYml(String yamlContent) {
        Yaml yaml = new Yaml();
        Map<String, Map<String, Object>> raw = yaml.load(yamlContent);
        parseMap(raw);
        return this;
    }

    // --- Wczytanie z JSON (String) ---
    public ConfigBuilder fromJson(String jsonContent) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, Object>> raw = mapper.readValue(jsonContent, Map.class);
        parseMap(raw);
        return this;
    }

    // --- Wczytanie z pliku ---
    public ConfigBuilder fromFile(Path path) throws Exception {
        String content = Files.readString(path);
        if (path.toString().endsWith(".yml") || path.toString().endsWith(".yaml"))
            return fromYml(content);
        else
            return fromJson(content);
    }

    private void parseMap(Map<String, Map<String, Object>> raw) {
        for (var entry : raw.entrySet()) {
            Map<String, Object> map = entry.getValue();
            SelectorDefinition def = new SelectorDefinition();
            def.setListSelector((String) map.get("listSelector"));
            def.setFieldSelectors((Map<String, String>) map.get("fieldSelectors"));
            selectors.put(entry.getKey(), def);
        }
    }

    // --- Walidacja konfiguracji ---
    public ConfigBuilder validate() {
        for (var entry : selectors.entrySet()) {
            String key = entry.getKey();
            SelectorDefinition def = entry.getValue();

            if (def.getListSelector() == null || def.getListSelector().isEmpty())
                throw new IllegalArgumentException("Brak listSelector dla sekcji: " + key);

            if (def.getFieldSelectors() == null || def.getFieldSelectors().isEmpty())
                throw new IllegalArgumentException("Brak fieldSelectors dla sekcji: " + key);
        }
        return this;
    }

    public Map<String, SelectorDefinition> build() {
        return Collections.unmodifiableMap(selectors);
    }
    
}
