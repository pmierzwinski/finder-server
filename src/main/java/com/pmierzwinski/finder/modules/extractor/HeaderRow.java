package com.pmierzwinski.finder.modules.extractor;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class HeaderRow {
    private String title;

    @Override
    public String toString() {
        return "HeaderRow{title='%s'}".formatted(title);
    }
}