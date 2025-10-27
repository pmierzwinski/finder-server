package com.pmierzwinski.finder.lib.scrapi;

import java.util.Map;

public class SelectorDefinition {
    private String listSelector;
    private Map<String, String> fieldSelectors;

    public String getListSelector() { return listSelector; }
    public void setListSelector(String listSelector) { this.listSelector = listSelector; }

    public Map<String, String> getFieldSelectors() { return fieldSelectors; }
    public void setFieldSelectors(Map<String, String> fieldSelectors) { this.fieldSelectors = fieldSelectors; }
}