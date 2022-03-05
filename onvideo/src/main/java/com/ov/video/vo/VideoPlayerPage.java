package com.ov.video.vo;

import com.ov.video.entity.OvComment;
import com.ov.video.entity.OvUser;
import lombok.Data;

import java.util.List;

@Data
public class VideoPlayerPage {

    VideoInfoPlayer videoInfoPlayer;
    OvUser uinfo;
    List<CommitInfo> commitInfos;
    String player;

}
