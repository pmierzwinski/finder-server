package com.example.finder.util;

public class SelectorBuilder {
    private final StringBuilder sb = new StringBuilder();

    public static SelectorBuilder create() {
        return new SelectorBuilder();
    }

    public SelectorBuilder tag(String tag) {
        sb.append(tag);
        return this;
    }

    public SelectorBuilder id(String id) {
        sb.append("#").append(id);
        return this;
    }

    public SelectorBuilder clazz(String clazz) {
        sb.append(".").append(clazz);
        return this;
    }

    public SelectorBuilder attr(String attr) {
        sb.append("[").append(attr).append("]");
        return this;
    }

    public SelectorBuilder child() {
        sb.append(" > ");
        return this;
    }

    public SelectorBuilder descendant() {
        sb.append(" ");
        return this;
    }

    public String build(){
        return sb.toString().trim();
    }
}
