package com.pmierzwinski.finder.modules.videos.factory;

import com.pmierzwinski.finder.modules.scraping.candidate.VideoCandidate;
import com.pmierzwinski.finder.modules.videos.db.VideoRow;

public class VideoFactory {

    //todo - zrobić candidate jako nullowy objeki i tu go weryfikować + moze interface Video ktory beda implementować i videoRow i candidate
    public static VideoRow fromCandidate(VideoCandidate candidate) {
        return new VideoRow(
                candidate.getWebsiteName(),
                candidate.getTitle(),
                candidate.getDescription(),
                candidate.getContentUrl(),
                candidate.getImageUrl()
        );
    }
}
