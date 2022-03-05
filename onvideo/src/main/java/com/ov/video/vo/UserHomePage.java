package com.ov.video.vo;

import com.ov.video.entity.OvUser;
import lombok.Data;

import java.util.List;

@Data
public class UserHomePage {
    OvUser ovUser;
    List<VideoInfo> videoInfoList;
    List<CommitInfo> commitInfoList;
    List<DynamicInfo> dynamicInfoList;

}
