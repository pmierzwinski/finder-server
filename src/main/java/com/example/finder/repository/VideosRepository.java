package com.example.finder.repository;

import com.example.finder.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VideosRepository extends JpaRepository<Video, Long> {
//    List<Video> findBySite(String site, LocalDate date);
}
