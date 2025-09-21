package com.example.finder.repository;

import com.example.finder.model.ScrapedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScrapedItemRepository extends JpaRepository<ScrapedItem, Long> {
    List<ScrapedItem> findBySiteAndDate(String site, LocalDate date);
}
