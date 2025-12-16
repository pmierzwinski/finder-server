package com.pmierzwinski.finder.modules.scraping.db;

import com.pmierzwinski.finder.modules.scraping.model.ScrapingStatus;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ScrapingStatusDataSourceImpl implements ScrapingStatusDataSource {

    private final ScrapingStatusRepository dbHandler;

    private final ScrapingStatusMapper mapper = Mappers.getMapper(ScrapingStatusMapper.class);

    ScrapingStatusDataSourceImpl(
            ScrapingStatusRepository dbHandler
    ) {
        this.dbHandler = dbHandler;
    }

    @Override
    public List<ScrapingStatus> findLatestStatusesPerPage() {
        return mapper.toScrapingStatusList(dbHandler.findLatestStatusesPerPage());
    }

    @Override
    public ScrapingStatus findTopByPageIdOrderByStartTimeDesc(String pageId) {
        return mapper.toScrapingStatus(dbHandler.findTopByPageIdOrderByStartTimeDesc(pageId));
    }

    @Override
    public List<ScrapingStatus> findTop10ByPageIdOrderByStartTimeDesc(String pageId) {
        return mapper.toScrapingStatusList(dbHandler.findTop10ByPageIdOrderByStartTimeDesc(pageId));
    }
}
