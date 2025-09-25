package com.example.finder.modules.videos.repository;

import com.example.finder.modules.videos.db.VideoRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideosRepository extends JpaRepository<VideoRow, Long> {
//    List<Video> findBySite(String site, LocalDate date);
}
