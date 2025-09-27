package com.pmierzwinski.finder.modules.videos.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "videos") // jawnie ustaw nazwę tabeli
@Getter
@NoArgsConstructor
public class VideoRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String websiteName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String imageUrl;


    public VideoRow(String websiteName, String title, String description, String url, String imageUrl) {
        this.websiteName = websiteName;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "VideoEntity{" +
                "id=" + id +
                ", websiteName='" + websiteName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
