package com.pmierzwinski.finder.modules.extractor;

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

    public SelectorDefinition(String css) {
        this.css = css;
    }

    public String extractValue(Element element) {
        if (element == null || css == null) return null;
        Element found = element.selectFirst(css);
        if (found == null) return null;

        return (tag == null || tag.isBlank() || "text".equalsIgnoreCase(tag))
                ? found.text()
                : found.attr(tag);
    }
}