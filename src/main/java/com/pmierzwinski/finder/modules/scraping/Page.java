package com.pmierzwinski.finder.modules.scraping;

import com.pmierzwinski.finder.modules.videos.db.VideoModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Page {
    private List<VideoModel> videos;
}