package com.pmierzwinski.finder.modules.videos.db;

import com.pmierzwinski.finder.modules.videos.model.Video;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class VideosDataSourceImpl implements VideosDataSource {

    private final VideosRepository dbHandler;

    private final VideosMapper mapper = Mappers.getMapper(VideosMapper.class);

    VideosDataSourceImpl(
            VideosRepository dbHandler
    ) {
        this.dbHandler = dbHandler;
    }

    @Override
    public List<Video> findAll() {
        return mapper.toDomainList(dbHandler.findAll());
    }

    @Override
    public void saveAll(List<Video> videos) {
        var entities = mapper.toEntityList(videos);
        dbHandler.saveAll(entities);
    }

    @Override
    public void deleteByPage(String page) {
        dbHandler.deleteByPage(page);
    }
}