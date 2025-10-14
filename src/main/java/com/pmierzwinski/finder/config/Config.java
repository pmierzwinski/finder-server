package com.pmierzwinski.finder.config;

import com.pmierzwinski.finder.modules.extractor.HeaderRow;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "scraper")
public class Config {

    private List<PageConfig> pages;

    @Getter @Setter
    public static class PageConfig {
        private String id;
        private String name;
        private VideoGroupDefinition videos;
        private HeaderGroupDefinition headers;
    }

    @Getter @Setter
    public static class LibSelector {
        private String css;
        private String tag;
    }

    @Getter @Setter
    public static class HeaderGroupDefinition extends GroupDefinition<HeaderRow> {
        private Config.LibSelector title;

        @Override
        public Class<HeaderRow> getTargetType() {
            return HeaderRow.class;
        }
    }

    @Getter @Setter
    public static class VideoGroupDefinition extends GroupDefinition<VideoRow> {
        private Config.LibSelector title;
        private Config.LibSelector imageUrl;

        @Override
        public Class<VideoRow> getTargetType() {
            return VideoRow.class;
        }
    }
    @Getter
    @Setter
    public static abstract class GroupDefinition<T> {
        private String groupCss;
        private List<T> items = new ArrayList<>();

        public abstract Class<T> getTargetType();

        public Map<String, Config.LibSelector> asFieldMap() {
            Map<String, Config.LibSelector> map = new LinkedHashMap<>();
            for (Field f : getClass().getDeclaredFields()) {
                if (Config.LibSelector.class.isAssignableFrom(f.getType())) {
                    try {
                        f.setAccessible(true);
                        Config.LibSelector sel = (Config.LibSelector) f.get(this);
                        if (sel != null) map.put(f.getName(), sel);
                    } catch (IllegalAccessException ignored) {}
                }
            }
            return map;
        }
    }

}
