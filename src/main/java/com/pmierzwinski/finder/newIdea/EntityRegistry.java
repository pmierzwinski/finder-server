package com.pmierzwinski.finder.newIdea;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EntityRegistry implements ApplicationContextAware {

    private final Map<String, Class<?>> registry = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // Spring wie o wszystkich klasach z adnotacją @Entity (czyli naszych modelach)
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            try {
                Class<?> clazz = Class.forName(applicationContext.getType(beanName).getName());
                if (clazz.isAnnotationPresent(ScrapedSection.class)) {
                    ScrapedSection annotation = clazz.getAnnotation(ScrapedSection.class);
                    registry.put(annotation.key(), clazz);
                }
            } catch (Exception ignored) { }
        }
    }

    public Class<?> getEntityType(String key) {
        return registry.get(key);
    }
}