package com.pmierzwinski.finder.modules.testing;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class PageConfig extends PageBase {
    private Map<String, FieldGroupConfig> extractors = new LinkedHashMap<>();

    public Map<String, FieldGroupConfig> getExtractors() { return extractors; }
    public void setExtractors(Map<String, FieldGroupConfig> extractors) { this.extractors = extractors; }
}