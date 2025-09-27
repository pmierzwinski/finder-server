package com.pmierzwinski.finder.modules.videos.repository;

import com.pmierzwinski.finder.modules.videos.db.VideoRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideosRepository extends JpaRepository<VideoRow, Long> {
//    List<Video> findBySite(String site, LocalDate date);
}
