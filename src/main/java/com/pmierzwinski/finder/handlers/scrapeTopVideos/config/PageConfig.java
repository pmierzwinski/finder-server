package com.pmierzwinski.finder.handlers.scrapeTopVideos.config;

import com.pmierzwinski.finder.lib.scrapi.ScrapiGroupSelector;
import com.pmierzwinski.finder.lib.scrapi.ScrapiPage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageConfig extends ScrapiPage {
    ScrapiGroupSelector<PageVideoConfig> videos;
}

