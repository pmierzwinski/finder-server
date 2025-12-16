package com.pmierzwinski.finder.modules.scraping.db;

import com.pmierzwinski.finder.modules.scraping.model.PageVideoModel;
import com.pmierzwinski.finder.modules.scraping.model.ScrapingStatus;
import com.pmierzwinski.finder.modules.videos.model.Video;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScrapingStatusMapper {

    Video toVideo(PageVideoModel video);

    List<Video> toVideoList(List<PageVideoModel> videos);
    
    ScrapingStatus toScrapingStatus(ScrapingStatusEntity entity);

    List<ScrapingStatus> toScrapingStatusList(List<ScrapingStatusEntity> entities);
}