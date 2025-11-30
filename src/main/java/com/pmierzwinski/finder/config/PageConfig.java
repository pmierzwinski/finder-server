package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.lib.scrapi.ScrapiGroupSelector;
import com.pmierzwinski.finder.lib.scrapi.ScrapiPageConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageConfig extends ScrapiPageConfig {
    ScrapiGroupSelector<PageVideoConfig> videos;
}

