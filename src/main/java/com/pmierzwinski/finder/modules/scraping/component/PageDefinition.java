package com.pmierzwinski.finder.modules.scraping.component;

public record PageDefinition(PageId id, String name, String domain, String dataUrl, String verifySelector) {

}