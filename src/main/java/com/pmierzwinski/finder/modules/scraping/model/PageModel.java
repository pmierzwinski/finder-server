package com.pmierzwinski.finder.modules.scraping.model;

import com.pmierzwinski.finder.lib.scrapi.ScrapiPage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageModel extends ScrapiPage {
    private List<PageVideoModel> videos;
}