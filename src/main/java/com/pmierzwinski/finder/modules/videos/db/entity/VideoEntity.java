package com.pmierzwinski.finder.modules.videos.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageId;
    private String name;
    private String url;
    private String imgUrl;

    public VideoEntity() {

    }

    public VideoEntity(String pageId, String name, String url, String imgUrl) {
        this.pageId = pageId;
        this.name = name;
        this.url = url;
        this.imgUrl = imgUrl;
    }
}
