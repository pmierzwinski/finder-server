package com.example.finder.repository;

import com.example.finder.model.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideosRepository extends JpaRepository<VideoEntity, Long> {
//    List<Video> findBySite(String site, LocalDate date);
}
