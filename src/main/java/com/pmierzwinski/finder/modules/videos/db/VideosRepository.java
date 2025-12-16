package com.pmierzwinski.finder.modules.videos.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VideosRepository extends JpaRepository<VideoEntity, Long> {
    void deleteByPage(String page);
}