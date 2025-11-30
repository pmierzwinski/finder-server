package com.pmierzwinski.finder.modules.scraping.config;

import com.pmierzwinski.finder.lib.scrapi.ScrapiCssSelector;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class PageVideoConfig {
    ScrapiCssSelector name;
    ScrapiCssSelector url;
    ScrapiCssSelector imgUrl;
}