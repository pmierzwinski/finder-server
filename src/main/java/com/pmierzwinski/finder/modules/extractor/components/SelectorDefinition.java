package com.pmierzwinski.finder.modules.extractor.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Element;

@Getter
@Setter
@NoArgsConstructor
public class SelectorDefinition {

    private String css;
    private String tag;

    public String extractValue(Element context) {
        if (context == null || css == null) return null;

        Element found = context.selectFirst(css);
        if (found == null) return null;

        if (tag == null || tag.isBlank() || "text".equalsIgnoreCase(tag)) {
            return found.text();
        }

        return found.attr(tag);
    }
}