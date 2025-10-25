package com.pmierzwinski.finder.usage;

import com.pmierzwinski.finder.scrapi.models.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageConfig extends BasePageConfig {
    public BaseGroupElementConfig<VideoConfig> videos;

    @Override
    public BasePageModel getPageModel() {
        return new PageModel();
    }

    @Getter @Setter
    public static class VideoConfig extends BaseElementConfig<VideoModel> {
        public ValueSelector title;
        public ValueSelector url;
        public ValueSelector imgUrl;

        public VideoModel getModel() {
            return new VideoModel();
        }

    }
}
