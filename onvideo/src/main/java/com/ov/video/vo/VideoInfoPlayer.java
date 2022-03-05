package com.ov.video.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoInfoPlayer {
    String title,msg;
    Long views,commits;
    LocalDateTime date;
}
