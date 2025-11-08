package com.pmierzwinski.finder.handlers.scrapeTopVideos.config;

import com.pmierzwinski.finder.lib.scrapi.ScrapiCssSelector;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class PageVideoConfig {
    ScrapiCssSelector name;
    ScrapiCssSelector url;
}