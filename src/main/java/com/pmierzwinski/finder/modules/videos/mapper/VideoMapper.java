package com.pmierzwinski.finder.modules.videos.mapper;

import com.pmierzwinski.finder.modules.videos.domain.model.Video;
import com.pmierzwinski.finder.modules.videos.db.entity.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    Video toDomain(VideoEntity entity);
    VideoEntity toEntity(Video video);

    List<Video> toDomainList(List<VideoEntity> entities);
    List<VideoEntity> toEntityList(List<Video> videos);
}
