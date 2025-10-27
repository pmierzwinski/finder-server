package com.pmierzwinski.finder.lib.scrapi;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScrapiGroupSelector<T> {
    String listCssSelector;
    T fieldSelectors;
}