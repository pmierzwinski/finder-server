package com.example.finder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private SiteName websiteName;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
}