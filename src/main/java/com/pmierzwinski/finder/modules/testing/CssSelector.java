package com.pmierzwinski.finder.modules.testing;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CssSelector {
    private String css;
    private ValueType valueType = ValueType.TEXT;
    private String attributeName;

    public enum ValueType { TEXT, HTML, SRC, HREF, ATTRIBUTE }

}