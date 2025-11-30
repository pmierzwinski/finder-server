package com.pmierzwinski.finder.modules.videos.db.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String page;
    private String name;
    private String url;
    private String imgUrl;
}
