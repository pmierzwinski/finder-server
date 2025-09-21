package com.example.finder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String websiteName;
    private String title;
    private String description;
    private String url;
    private String imageUrl;


    public Video(String websiteName, String title, String description, String url, String imageUrl) {
        this.websiteName = websiteName;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", websiteName='" + websiteName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
