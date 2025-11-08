package com.pmierzwinski.finder.lib.scrapi;

public class ScrapiConfig {
    private final Object configResult; // obecny wynik z ConfigBuilder.build()
    private final ScrapiPage pageConfig;

    public ScrapiConfig(Object configResult, ScrapiPage pageConfig) {
        this.configResult = configResult;
        this.pageConfig = pageConfig;
    }

    public Object getConfigResult() {
        return configResult;
    }

    public ScrapiPage getPageConfig() {
        return pageConfig;
    }
}
