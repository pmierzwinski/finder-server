package com.pmierzwinski.finder.scrapi;

import com.pmierzwinski.finder.scrapi.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;

public class Extractor {

    public static BasePageModel extract(String html, BasePageConfig config) {
        try {
            return Extractor.internalExtract(html, config);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private static BasePageModel internalExtract(String html, BasePageConfig config) throws NoSuchFieldException, IllegalAccessException {
        var model = config.getPageModel();
        model.setHtml(html);
        Document doc = Jsoup.parse(html);

        Field[] modelFields = model.getClass().getDeclaredFields();
        for (Field modelField : modelFields) {
            modelField.setAccessible(true);
            Field configField = config.getClass().getDeclaredField(modelField.getName());

            BaseGroupElementConfig<?> groupConfig = (BaseGroupElementConfig<?>) configField.get(config);
            BaseElementConfig elementConfig = (BaseElementConfig) groupConfig.getElementConfig();

            Elements extractedElements = doc.select(groupConfig.getGroupSelector().getCss());
            for (Element extractedElement : extractedElements) {

                Field[] elementConfigFields = elementConfig.getClass().getDeclaredFields();

                var elementModel = elementConfig.getModel();
                for (Field elementConfigField : elementConfigFields) {
                    ValueSelector valueSelector = (ValueSelector) elementConfigField.get(elementConfig);

                    Elements element = extractedElement.select(valueSelector.css);
                    String value = switch (valueSelector.getTag()) {
                        case "text" -> element.text();
                        case "src", "href" -> element.attr(valueSelector.getTag());
                        default -> element.attr(valueSelector.getTag());
                    };

                    var modelField2 = elementModel.getClass().getDeclaredField(elementConfigField.getName());
                    modelField2.setAccessible(true);
                    modelField2.set(elementModel, value);
                }
            }
        }

        return model;
    }

}
