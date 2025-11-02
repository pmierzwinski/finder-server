package com.pmierzwinski.finder.lib.scrapi;

import java.util.List;
import java.util.Map;

public class ScrapiAppConfig {
    String id;
    String dataUrl;
    List<ScrapiCssSelector> verificationActions;
    Map<String, SelectorDefinition> pageSelectors;

    public ScrapiAppConfig(String id, String dataUrl, List<ScrapiCssSelector> verificationActions, Map<String, SelectorDefinition> pageSelectors) {
        this.id = id;
        this.dataUrl = dataUrl;
        this.verificationActions = verificationActions;
        this.pageSelectors = pageSelectors;
    }

}
