package com.pmierzwinski.finder.usage;

import com.pmierzwinski.finder.scrapi.models.BasePageModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PageModel extends BasePageModel {

    List<VideoModel> videos;

}
