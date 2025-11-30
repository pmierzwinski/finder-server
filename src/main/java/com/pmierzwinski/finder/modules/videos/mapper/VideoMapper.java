package com.pmierzwinski.finder.modules.videos.mapper;

import com.pmierzwinski.finder.modules.videos.model.Video;
import com.pmierzwinski.finder.modules.videos.model.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VideoMapper {

    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    VideoEntity toEntity(Video video);

    Video toDomain(VideoEntity entity);
}
