package com.pmierzwinski.finder.scrapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseElementConfig<T> {
    public abstract T getModel();
}
