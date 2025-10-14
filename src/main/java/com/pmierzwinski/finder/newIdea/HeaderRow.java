package com.pmierzwinski.finder.newIdea;

import com.pmierzwinski.finder.config.Config;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "headers")
@Getter
@NoArgsConstructor
@ScrapedSection(key = "headers")
public class HeaderRow implements PageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String page;
    private String title;
    private String subtitle;

    @Override
    public void enrich(Config.PageConfig config) {
        this.page = config.getName();
    }
}
