package com.example.finder.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ScrapedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String site;       // np. youtube-top
    private String title;      // tytuł filmu
    private String url;        // link
    private LocalDate date;    // dzień scrapingu (cache)
}
