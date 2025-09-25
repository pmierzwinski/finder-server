package com.example.finder.modules.videos.model;

import com.example.finder.modules.videos.db.VideoRow;

public class VideoCandidate {
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
