package com.pmierzwinski.finder.scrapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseGroupElementConfig<T> {

    public GroupSelector groupSelector;
    public T elementConfig;

}
