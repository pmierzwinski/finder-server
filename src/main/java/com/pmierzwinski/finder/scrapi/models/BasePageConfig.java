package com.pmierzwinski.finder.scrapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class BasePageConfig {
    public String id;
    public String contentUrl;

    public abstract BasePageModel getPageModel();
}
