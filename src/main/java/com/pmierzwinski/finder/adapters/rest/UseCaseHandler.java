package com.pmierzwinski.finder.adapters.rest;

import com.pmierzwinski.finder.app.UpdateTopVideosUseCase;
import com.pmierzwinski.finder.config.Config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UseCaseHandler {

    private final Config config;
    private final UpdateTopVideosUseCase updateTopVideosUseCase;

    public UseCaseHandler(Config config, UpdateTopVideosUseCase updateTopVideosUseCase) {
        this.config = config;
        this.updateTopVideosUseCase = updateTopVideosUseCase;
    }

    @GetMapping("/updateTopVideos")
    public void updateTopVideos() {
        updateTopVideosUseCase.handle(config);
    }
}
