package com.pmierzwinski.finder.modules.testing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public final class Extractor {

    public static <P extends PageConfig> P extract(String html, P pageConfig) {
        Document doc = Jsoup.parse(html);
        Class<?> pageClass = pageConfig.getClass();

        for (Field field : pageClass.getDeclaredFields()) {
            if (!isList(field)) continue;

            String key = getGroupKey(field);
            FieldGroupConfig group = getGroupOrThrow(pageConfig, key);
            Class<?> itemType = getListItemType(field);
            List<Object> items = extractGroup(doc, group, itemType);

            set(field, pageConfig, items);
        }

        validateNoOrphanExtractors(pageConfig, pageClass);
        return pageConfig;
    }

    private static boolean isList(Field f) {
        return List.class.isAssignableFrom(f.getType());
    }

    private static String getGroupKey(Field f) {
        GroupKey ann = f.getAnnotation(GroupKey.class);
        return (ann != null && !ann.value().isBlank()) ? ann.value() : f.getName();
    }

    private static FieldGroupConfig getGroupOrThrow(PageConfig cfg, String key) {
        FieldGroupConfig g = cfg.getExtractors().get(key);
        if (g == null)
            throw new IllegalStateException("Brak extractor dla grupy: " + key);
        return g;
    }

    private static Class<?> getListItemType(Field listField) {
        Type g = listField.getGenericType();
        if (g instanceof ParameterizedType pt) {
            Type arg = pt.getActualTypeArguments()[0];
            if (arg instanceof Class<?> c) return c;
        }
        throw new IllegalStateException("Nie można ustalić typu elementu listy: " + listField);
    }

    private static List<Object> extractGroup(Document doc, FieldGroupConfig group, Class<?> itemType) {
        Elements elements = doc.select(group.getGroupCssSelector());
        List<Object> result = new ArrayList<>(elements.size());

        for (Element el : elements) {
            Object item = newInstance(itemType);

            for (Map.Entry<String, CssSelector> e : group.getFields().entrySet()) {
                String fieldName = e.getKey();
                CssSelector sel = e.getValue();

                Elements selEls = "&".equals(sel.getCss()) ? new Elements(el) : el.select(sel.getCss());
                String value = switch (sel.getValueType()) {
                    case TEXT -> selEls.text();
                    case HTML -> selEls.html();
                    case SRC -> selEls.attr("src");
                    case HREF -> selEls.attr("href");
                    case ATTRIBUTE -> selEls.attr(sel.getAttributeName());
                };
                setByName(item, fieldName, value);
            }
            result.add(item);
        }
        return result;
    }

    private static Object newInstance(Class<?> type) {
        try { return type.getDeclaredConstructor().newInstance(); }
        catch (Exception e) { throw new IllegalStateException("Nie mogę utworzyć " + type, e); }
    }

    private static void set(Field field, Object target, Object value) {
        try { field.setAccessible(true); field.set(target, value); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    private static void setByName(Object target, String name, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(target, value);
        } catch (NoSuchFieldException nf) {
            throw new IllegalStateException("Pole " + name + " nie istnieje w " + target.getClass().getSimpleName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void validateNoOrphanExtractors(PageConfig cfg, Class<?> pageClass) {
        Set<String> listFieldKeys = Arrays.stream(pageClass.getDeclaredFields())
                .filter(Extractor::isList)
                .map(Extractor::getGroupKey)
                .collect(Collectors.toSet());

        for (String key : cfg.getExtractors().keySet()) {
            if (!listFieldKeys.contains(key))
                throw new IllegalStateException("Extractor \"" + key + "\" nie ma odpowiadającego pola List<?> w klasie " + pageClass.getSimpleName());
        }
    }
}