package com.pmierzwinski.finder.adapters.scheduler;

import com.pmierzwinski.finder.app.UpdateTopVideosUseCase;
import com.pmierzwinski.finder.config.Config;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJobs {

    private final UpdateTopVideosUseCase updateTopVideosUseCase;
    private final Config config;

    public CronJobs(UpdateTopVideosUseCase updateTopVideosUseCase, Config config) {
        this.updateTopVideosUseCase = updateTopVideosUseCase;
        this.config = config;
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void syncVideos() {
        updateTopVideosUseCase.handle(config);
    }
}
