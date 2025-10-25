package com.pmierzwinski.finder.modules.testing;

import java.util.Map;

public class FieldGroupConfig {
    private String groupCssSelector;
    private Map<String, CssSelector> fields;

    public String getGroupCssSelector() { return groupCssSelector; }
    public void setGroupCssSelector(String groupCssSelector) { this.groupCssSelector = groupCssSelector; }

    public Map<String, CssSelector> getFields() { return fields; }
    public void setFields(Map<String, CssSelector> fields) { this.fields = fields; }
}