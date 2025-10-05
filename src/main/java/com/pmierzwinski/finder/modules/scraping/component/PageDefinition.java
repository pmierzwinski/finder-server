package com.pmierzwinski.finder.modules.scraping.component;

import lombok.Getter;

@Getter
public class PageDefinition {

    private PageId id;
    private String name;
    private String domain;
    private String dataUrl;
    private String verifySelector;

    public PageDefinition(PageId id, String name, String domain, String dataUrl, String verifySelector) {
        this.id = id;
        this.name = name;
        this.domain = domain;
        this.dataUrl = dataUrl;
        this.verifySelector = verifySelector;
    }
}