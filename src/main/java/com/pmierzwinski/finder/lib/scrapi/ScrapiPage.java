package com.pmierzwinski.finder.lib.scrapi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ScrapiPage {
    String id;
    String dataUrl;
    List<ScrapiCssSelector> verificationActions;
}