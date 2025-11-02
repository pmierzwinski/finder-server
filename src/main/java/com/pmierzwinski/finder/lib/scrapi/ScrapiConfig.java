package com.pmierzwinski.finder.lib.scrapi;

public class ScrapiConfig {
    private final Object configResult; // obecny wynik z ConfigBuilder.build()
    private final ScrapiPageConfig pageConfig;

    public ScrapiConfig(Object configResult, ScrapiPageConfig pageConfig) {
        this.configResult = configResult;
        this.pageConfig = pageConfig;
    }

    public Object getConfigResult() {
        return configResult;
    }

    public ScrapiPageConfig getPageConfig() {
        return pageConfig;
    }
}
