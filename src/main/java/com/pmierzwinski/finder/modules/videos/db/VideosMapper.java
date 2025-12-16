package com.pmierzwinski.finder.modules.videos.db;

import com.pmierzwinski.finder.modules.videos.model.Video;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VideosMapper {
    Video toDomain(VideoEntity entity);

    VideoEntity toEntity(Video video);

    List<Video> toDomainList(List<VideoEntity> entities);

    List<VideoEntity> toEntityList(List<Video> videos);
}
