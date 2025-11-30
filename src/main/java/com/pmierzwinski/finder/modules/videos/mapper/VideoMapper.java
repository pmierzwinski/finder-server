package com.pmierzwinski.finder.modules.videos.mapper;

import com.pmierzwinski.finder.modules.videos.domain.model.Video;
import com.pmierzwinski.finder.modules.videos.db.entity.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    VideoEntity toEntity(Video video);
    Video toDomain(VideoEntity entity);

    List<VideoEntity> toEntityList(List<Video> videos);
    List<Video> toDomainList(List<VideoEntity> entities);
}