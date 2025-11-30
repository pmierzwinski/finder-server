package com.pmierzwinski.finder.modules.scraping.mapper;

import com.pmierzwinski.finder.modules.scraping.domain.model.PageVideoModel;
import com.pmierzwinski.finder.modules.videos.domain.model.Video;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScrapeMapper {

    Video toVideo(PageVideoModel video);

    List<Video> toVideoList(List<PageVideoModel> videos);
}