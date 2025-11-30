package com.pmierzwinski.finder.modules.videos.db.repository;

import com.pmierzwinski.finder.modules.videos.db.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends JpaRepository<VideoEntity, Long> {
    void deleteByPage(String page);
}