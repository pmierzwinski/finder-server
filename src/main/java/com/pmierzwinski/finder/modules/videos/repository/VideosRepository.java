package com.pmierzwinski.finder.modules.videos.repository;

import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.data.jpa.repository.JpaRepository;

//todo change it to track old videos - so no deleteAll
public interface VideosRepository extends JpaRepository<VideoRow, Long> {
    void deleteByPage(String page);
}
