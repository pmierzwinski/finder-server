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
    private String page;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String imageUrl;


    public VideoRow(String page, String title, String description, String url, String imageUrl) {
        this.page = page;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "VideoEntity{" +
                "id=" + id +
                ", websiteName='" + page + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public static class VideoCandidate {
        String websiteName;
        String contentUrl;
        String title;
        String description;
        String imageUrl;

        public VideoCandidate(String websiteName, String contentUrl, String title, String description, String imageUrl) {
            this.websiteName = websiteName;
            this.contentUrl = contentUrl;
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
        }

        public boolean isValid() {
            return notBlank(websiteName) && notBlank(contentUrl) && notBlank(title)
                    && notBlank(description) && notBlank(imageUrl);
        }

        private boolean notBlank(String s) {
            return s != null && !s.isBlank();
        }

        public VideoRow toEntity() {
            return new VideoRow(
                    websiteName,
                    title,
                    description,
                    contentUrl,
                    imageUrl
            );
        }
    }
}
